package com.golden.pharaoh.numbers.presentation.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun LevelScreen(onLevelSelect: (Int) -> Unit, onBackClicked: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LevelPreferences", Context.MODE_PRIVATE)

    // Используем LevelManager с sharedPreferences
    val levelManager = remember { LevelManager(sharedPreferences) }

    Box {
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Сетка уровней 2 столбца по 5 уровней (всего 10 уровней)
            for (row in 0 until 7) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (col in 0 until 2) {
                        val level = row * 2 + col + 1
                        val isUnlocked = levelManager.isLevelUnlocked(level)

                        Box(
                            modifier = Modifier
                                .size(150.dp, 75.dp)  // Уменьшенный размер кнопки уровня
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable(enabled = isUnlocked) {
                                    if (isUnlocked) {
                                        SoundManager.playSound()
                                        onLevelSelect(level)
                                    }
                                }
                        ) {
                            val backgroundImage =
                                painterResource(id = R.drawable.rec_act)


                            Image(
                                painter = backgroundImage,
                                contentDescription = "Level $level",
                                modifier = Modifier.fillMaxSize()
                            )
                            OutlinedText(
                                text = "Lvl $level",
                                outlineColor = if (isUnlocked) Color.Red else Color.Black,
                                fillColor = if (isUnlocked) Color.Yellow else Color.Gray,
                                fontSize = 28.sp, // Немного уменьшенный размер шрифта
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(0.dp))

            // Кнопка "Back"
            Box(
                modifier = Modifier
                    .size(130.dp, 50.dp)
                    .clickable(onClick = onBackClicked)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rec), // Фон для кнопки
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                        .size(130.dp, 50.dp)
                )
                OutlinedText(
                    text = "Back",
                    outlineColor = Color.Red,
                    fillColor = Color.Yellow,
                    fontSize = 20.sp // Немного уменьшенный размер текста
                )
            }
        }
    }
}


class LevelManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val UNLOCKED_LEVELS_KEY = "unlockedLevels"
    }

    fun getUnlockedLevels(): Int {
        return sharedPreferences.getInt(
            UNLOCKED_LEVELS_KEY,
            1
        ) // По умолчанию разблокирован только 1 уровень
    }

    fun unlockNextLevel(levelCompleted: Int) {
        val currentUnlockedLevels = getUnlockedLevels()
        if (levelCompleted >= currentUnlockedLevels) {
            sharedPreferences.edit().putInt(UNLOCKED_LEVELS_KEY, levelCompleted + 1).apply()
        }
    }

    fun isLevelUnlocked(level: Int): Boolean {
        return level <= getUnlockedLevels()
    }
}