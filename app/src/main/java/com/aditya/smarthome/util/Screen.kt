package com.aditya.smarthome.util

sealed class Screen (val route: String) {
    object HomeScreen: Screen("home_screen_route")
    object LoginScreen: Screen("login_screen_route")
    object RegisterScreen: Screen("register_screen_route")
    object DetailScreen: Screen("detail_screen_route")
}