package com.example.sceneryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class Scenery(
    val name: String,
    val description: String,
    val imageRes: Int
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var darkTheme by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme =
                    if (darkTheme) darkColorScheme()
                    else lightColorScheme()
            ) {

                val sceneryList = listOf(
                    Scenery(
                        "Sunset",
                        "A sunset fills the sky with orange, pink and purple shades. " +
                                "It brings peace and warmth to the heart.",
                        R.drawable.s1
                    ),
                    Scenery(
                        "Moon",
                        "The moon glows softly in the dark sky surrounded by stars. " +
                                "It creates a calm and magical atmosphere.",
                        R.drawable.s2
                    ),
                    Scenery(
                        "Forest",
                        "A forest is full of tall trees, fresh air and soothing sounds. " +
                                "Nature feels alive and peaceful here.",
                        R.drawable.s3
                    )
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {

                        // Top Bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Scenery Explorer",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            IconButton(
                                onClick = { darkTheme = !darkTheme }
                            ) {
                                Icon(
                                    imageVector =
                                        if (darkTheme)
                                            Icons.Default.LightMode
                                        else
                                            Icons.Default.DarkMode,
                                    contentDescription = "Toggle Theme"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        LazyColumn {
                            items(sceneryList) { scenery ->
                                SceneryCard(scenery)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SceneryCard(scenery: Scenery) {

    var expanded by remember { mutableStateOf(false) }
    var favourite by remember { mutableStateOf(false) }

    val starColor by animateColorAsState(
        targetValue =
            if (favourite)
                MaterialTheme.colorScheme.primary
            else Color.Gray,
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = scenery.imageRes),
                    contentDescription = scenery.name,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 12.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = scenery.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                IconButton(
                    onClick = { favourite = !favourite }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Favorite",
                        tint = starColor
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = scenery.description,
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
