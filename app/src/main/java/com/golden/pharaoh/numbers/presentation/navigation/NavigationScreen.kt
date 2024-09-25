package com.golden.pharaoh.numbers.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.golden.pharaoh.numbers.domain.Levels
import com.golden.pharaoh.numbers.presentation.view.GameScreen
import com.golden.pharaoh.numbers.presentation.view.LevelScreen
import com.golden.pharaoh.numbers.presentation.view.MainMenuScreen
import com.golden.pharaoh.numbers.presentation.view.OptionsScreen
import com.golden.pharaoh.numbers.presentation.view.SplashScreen


@Composable
fun NavigationScreen(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.SplashScreen.route,
        modifier = Modifier,

        ) {

        composable(route = Destinations.SplashScreen.route) {
            SplashScreen {

                navHostController.navigate(Destinations.MainMenuScreen.route) {
                    popUpTo(Destinations.SplashScreen.route) {
                        inclusive = true
                    }


                }
            }
        }


        composable(route = Destinations.MainMenuScreen.route) {
            MainMenuScreen(
                onOptionClicked = {
                    navHostController.navigate(Destinations.OptionScreen.route) {
                        popUpTo(Destinations.MainMenuScreen.route) {
                        }
                    }
                },
                onStartClicked = {
                    navHostController.navigate(Destinations.LevelScreen.route) {
                        popUpTo(Destinations.MainMenuScreen.route) {
                        }
                    }
                }


            )
        }
        composable(route = Destinations.OptionScreen.route) {
            OptionsScreen(onBackClicked = { navHostController.navigateUp() })
        }
        composable(route = Destinations.LevelScreen.route) {
            LevelScreen(
                onLevelSelect = {


                        level ->
                    navHostController.navigate("${Destinations.GameScreen.route}/$level")


                },
                onBackClicked = { navHostController.navigateUp() }
            )
        }
        composable(
            route = "${Destinations.GameScreen.route}/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            val levelEnum =
                Levels.entries[level - 1] // Преобразуем номер уровня в соответствующий элемент enum
            GameScreen(
                levelEnum,
                onLevelSelect = {
                    navHostController.navigate(Destinations.LevelScreen.route) {
                        popUpTo(Destinations.MainMenuScreen.route) {
                        }
                    }

                },
                onSettingsClicked = {
                    navHostController.navigate(Destinations.OptionScreen.route) {

                    }
                },
                restartGame = {
                    navHostController.navigate("${Destinations.GameScreen.route}/${levelEnum.number}") {
                        popUpTo(Destinations.LevelScreen.route) {
                        }
                    }
                },
                onNextLevel = {  navHostController.navigate("${Destinations.GameScreen.route}/${levelEnum.number+1}") {
                    popUpTo(Destinations.LevelScreen.route) {
                    }
                }}

            )
        }

    }
}