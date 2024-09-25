package com.golden.pharaoh.numbers.presentation.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Color.Sphere.Challenge.gamecolor.data.SoundManager
import com.golden.pharaoh.numbers.R
import com.golden.pharaoh.numbers.presentation.navigation.OutlinedText


@Composable
fun MainMenuScreen(
    onStartClicked: () -> Unit,
    onOptionClicked: () -> Unit
) {
    val activity = LocalContext.current as? Activity
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Фоновое изображение
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.guard),  // Используем main_rec.xml
                contentDescription = null,

                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Изображение для всех кнопок
                Spacer(modifier = Modifier.height(20.dp))
                // Кнопка "Start"

                Box(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .clickable {
                            SoundManager.playSound()
                            onStartClicked()
                        }
                        .size(110.dp, 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        text = "PLAY",
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = 50.sp,
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                // Кнопка "Options"
                Box(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .clickable {
                            SoundManager.playSound()
                            onOptionClicked()
                        }
                        .size(240.dp, 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        text = "OPTIONS",
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = 50.sp,
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))
                // Кнопка "Exit"
                Box(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .clickable {
                            SoundManager.playSound()
                            activity?.finish()
                        }
                        .size(90.dp, 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        text = "EXIT",
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = 50.sp,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
