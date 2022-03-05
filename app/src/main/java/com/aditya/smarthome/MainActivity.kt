package com.aditya.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthome.ui.theme.SmartHomeTheme
import com.aditya.smarthome.util.Navigation
import com.aditya.smarthome.util.Screen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHomeTheme {
                val mainViewModel: MainViewModel = hiltViewModel()
                val navController = rememberAnimatedNavController()
                mainViewModel.userLoggedIn.value?.let { loggedIn ->
                    if(loggedIn) {
                        Navigation(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        )
                    } else {
                        Navigation(
                            navController = navController,
                            startDestination = Screen.LoginScreen.route
                        )
                    }
                }
            }
        }
    }
}

