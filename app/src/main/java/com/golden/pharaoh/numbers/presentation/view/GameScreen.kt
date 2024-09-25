package com.golden.pharaoh.numbers.presentation.view

import android.content.Context
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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.Color.Sphere.Challenge.gamecolor.data.SoundManager
import com.golden.pharaoh.numbers.R
import com.golden.pharaoh.numbers.domain.Levels
import com.golden.pharaoh.numbers.presentation.navigation.Destinations
import com.golden.pharaoh.numbers.presentation.navigation.OutlinedText
import com.golden.pharaoh.numbers.ui.theme.nujnoefont
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.sqrt

@Composable
fun GameScreen(
    level: Levels,
    onSettingsClicked: () -> Unit,
    onLevelSelect: () -> Unit,
    restartGame: () -> Unit,
    onNextLevel : () -> Unit

) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LevelPreferences", Context.MODE_PRIVATE)
    val levelManager = LevelManager(sharedPreferences)

    var gameEnd by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(level.countItems*60) }
    val gridSize = level.size
    val numTiles = gridSize * gridSize
    var tiles by remember { mutableStateOf(generateLevel(numTiles)) }
    val tileSize = 350.dp / gridSize





    LaunchedEffect(Unit) {
        while (!gameWon && !gameEnd) {
            delay(1000L)
            elapsedTime -= 1

            if (elapsedTime <= 0) {
                gameEnd = true
            }
        }
    }
    if (gameEnd || gameWon) {
        if(gameWon) levelManager.unlockNextLevel(level.ordinal + 1)
        // Переход на экран завершения игры
        GameEndScreen( isWin = gameWon,levels = level, onMenuClicked = onLevelSelect, restartGame = restartGame, onNextLevel = onNextLevel)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.Crop
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Верхняя панель
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(68.dp)
                            .clickable(

                                onClick = {
                                    SoundManager.playSound()
                                    onLevelSelect()
                                }
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(160.dp, 68.dp)
                            .paint(
                                painter = painterResource(id = R.drawable.rec_act),
                                contentScale = ContentScale.Crop
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        OutlinedText(
                            text = "${level.number} level",
                            outlineColor = Color.Red,
                            fillColor = Color.Yellow,
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )


                    }
                    Image(
                        painter = painterResource(id = R.drawable.optionsbutton),
                        contentDescription = "Options",
                        modifier = Modifier
                            .size(68.dp)
                            .clickable(
                                onClick = {
                                    SoundManager.playSound()
                                    onSettingsClicked()
                                }
                            )
                    )
                }

                Spacer(modifier = Modifier.height(106.dp))

                // Игровая доска
                Box(
                    modifier = Modifier.size(350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gameboard),
                        contentDescription = "Game Board",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.size(350.dp)
                    ) {
                        for (i in 0 until gridSize) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (j in 0 until gridSize) {
                                    val index = i * gridSize + j
                                    val tile = tiles[index]
                                    if (tile != 0) {
                                        Box(
                                            modifier = Modifier
                                                .size(tileSize)
                                                .paint(
                                                    painter = painterResource(id = R.drawable.plit),
                                                    contentScale = ContentScale.Crop
                                                )
                                                .clickable {
                                                    SoundManager.playSound()
                                                    tiles = moveTile(tiles, index)
                                                    gameWon = checkIfGameWon(tiles)
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            OutlinedText(
                                                text = tile.toString(),
                                                outlineColor = Color(0xFF6712A3),
                                                fillColor = Color.White,
                                                fontSize = 24.sp
                                            )
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(tileSize)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(56.dp))
                Box(
                    modifier = Modifier
                        .size(160.dp, 68.dp)
                        .paint(
                            painter = painterResource(id = R.drawable.time_rec),
                            contentScale = ContentScale.Crop
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    OutlinedText(
                        text = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            elapsedTime / 60,
                            elapsedTime % 60
                        ),
                        outlineColor = Color.Red,
                        fillColor = Color.Yellow,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )


                }


            }
        }
    }
}

fun checkIfGameWon(tiles: List<Int>): Boolean {
    // Идеальный порядок для плиток в игре 1, 2, 3, ..., N-1, 0
    return tiles == (1 until tiles.size).toList() + listOf(0)
}


fun generateLevel(size: Int): List<Int> {
    val numbers = (1 until size).shuffled().toMutableList()
    numbers.add(0) // Пустая плитка
    return numbers
}


fun moveTile(tiles: List<Int>, index: Int): List<Int> {
    val emptyIndex = tiles.indexOf(0)
    val size = sqrt(tiles.size.toDouble()).toInt()

    // Возможные перемещения с учетом границ сетки
    val validMoves = mutableListOf<Int>()
    if (index % size != 0) validMoves.add(index - 1) // Слева, если не на левом краю
    if ((index + 1) % size != 0) validMoves.add(index + 1) // Справа, если не на правом краю
    if (index >= size) validMoves.add(index - size) // Сверху, если не на верхнем краю
    if (index < tiles.size - size) validMoves.add(index + size) // Снизу, если не на нижнем краю

    return if (emptyIndex in validMoves) {
        tiles.toMutableList().apply {
            this[emptyIndex] = this[index]
            this[index] = 0
        }
    } else {
        tiles
    }
}



