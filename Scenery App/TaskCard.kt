package com.example.sceneryapp

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard1(
    title: String,
    description: String
) {

    var expanded by remember { mutableStateOf(false) }
    var favourite by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (favourite) 1.2f else 1f,
        animationSpec = tween(300),
        label = ""
    )

    val backgroundBrush = if (favourite) {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
            )
        )
    } else {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surface
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column(
            modifier = Modifier
                .background(backgroundBrush)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(
                    onClick = { favourite = !favourite }
                ) {
                    Icon(
                        imageVector =
                            if (favourite)
                                Icons.Filled.Star
                            else
                                Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = if (favourite)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray,
                        modifier = Modifier.scale(scale)
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}
