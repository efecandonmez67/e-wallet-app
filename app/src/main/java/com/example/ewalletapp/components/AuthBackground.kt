package com.example.ewalletapp.components // Kendi paket adınla aynı olduğuna emin ol

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import com.example.ewalletapp.ui.theme.BackgroundGray
import com.example.ewalletapp.ui.theme.PrimaryBlue

@Composable
fun AuthBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val topPath = Path().apply {
                moveTo(0f, 0f)
                lineTo(width * 0.7f, 0f)
                cubicTo(
                    width * 0.6f, height * 0.2f,
                    width * 0.2f, height * 0.1f,
                    0f, height * 0.4f
                )
                close()
            }
            drawPath(path = topPath, color = PrimaryBlue.copy(alpha = 0.9f))

            val bottomPath = Path().apply {
                moveTo(width, height)
                lineTo(width * 0.4f, height)
                cubicTo(
                    width * 0.6f, height * 0.8f,
                    width * 0.9f, height * 0.7f,
                    width, height * 0.6f
                )
                close()
            }
            drawPath(path = bottomPath, color = PrimaryBlue.copy(alpha = 0.7f))
        }

        content()
    }
}