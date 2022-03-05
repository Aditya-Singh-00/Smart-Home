package com.aditya.smarthome.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.aditya.smarthome.ui.screens.device_detail.DeviceDetailScreen
import com.aditya.smarthome.ui.screens.home.HomeScreen
import com.aditya.smarthome.ui.screens.login.LoginScreen
import com.aditya.smarthome.ui.screens.register.RegisterScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onSuccessfulLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                },
                onSignupClick = {
                    navController.navigate(Screen.RegisterScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                onSuccessfulRegistration = {
                    navController.navigate(Screen.HomeScreen.route)
                },
                onLoginClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.RegisterScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onDeviceClick = { id ->
                    navController.navigate(
                        Screen.DetailScreen.route + "/$id"
                    )

                },
                onLogout = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.DetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            DeviceDetailScreen()
        }
    }
}