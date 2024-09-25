package com.golden.pharaoh.numbers.presentation.navigation

sealed class Destinations(val route: String) {

    data object SplashScreen : Destinations("splash_screen")
    data object MainMenuScreen : Destinations("main_menu_screen")
    data object GameScreen : Destinations("game_screen")
    data object OptionScreen : Destinations("settings_screen")
    data object LevelScreen : Destinations("level_screen")

}