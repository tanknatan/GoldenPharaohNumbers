package com.golden.pharaoh.numbers.presentation.view

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.golden.pharaoh.numbers.R
import com.golden.pharaoh.numbers.domain.Levels
import com.golden.pharaoh.numbers.presentation.navigation.OutlinedText


@Composable
fun GameEndScreen(
    isWin: Boolean,
    levels: Levels,
    onMenuClicked: () -> Unit,
    onNextLevel : () -> Unit,
    restartGame: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            // Панель с результатами времени и очков
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedText(
                        text = if (isWin) {
                            "Winner"
                        } else {
                            "Defeated"
                        },
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = if (isWin) {
                            70.sp
                        } else {
                            50.sp
                        }
                    )


                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedText(
                        text = if (isWin) {
                            "${levels.number} level \n passed"
                        } else {
                            "Time is out"
                        },
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = 35.sp
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Box(
                        modifier = Modifier
                            .size(130.dp, 50.dp)
                            .clickable(
                                onClick = {
                                    if (isWin) {
                                        onNextLevel(

                                        )
                                    } else {
                                        restartGame(

                                        )
                                    }
                                }
                            )
                            .padding(bottom = 0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rec), // Фон для кнопки
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()

                        )
                        OutlinedText(
                            text = if (isWin) {
                                "Next"
                            } else {
                                "Retry"
                            },
                            outlineColor = Color.Red,
                            fillColor = Color.Yellow,
                            fontSize = 20.sp // Немного уменьшенный размер текста
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
                    .clickable(onClick = onMenuClicked),

                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.time_rec), // Фон для кнопки
                    contentDescription = null,


                    )
                OutlinedText(
                    text = "Home",
                    outlineColor = Color.Red,
                    fillColor = Color.Yellow,
                    fontSize = 50.sp // Немного уменьшенный размер текста
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun GameEndScreenPreview() {
//    GameEndScreen(isWin = false, levels = Levels.fourth, onMenuClicked = {})
//}


//fun restartGame(navController: NavController, currentDifficulty: Levels) {
//    // Navigate to the game screen with the current difficulty level
//    navController.navigate("game_screen/${currentDifficulty.name}") {
//        // Pop the back stack to clear the previous instance of the game screen
//        popUpTo("game_screen/${currentDifficulty.name}") { inclusive = true }
//    }
//}
//
//fun onNextLevel(navController: NavController, currentDifficulty: Levels) {
//    // Navigate to the game screen with the next difficulty level
//    navController.navigate("game_screen/${currentDifficulty.ordinal + 1}") {
//        // Pop the back stack to clear the previous instance of the game screen
//        popUpTo("game_screen/${currentDifficulty.ordinal + 1}") { inclusive = true }
//    }
//}


