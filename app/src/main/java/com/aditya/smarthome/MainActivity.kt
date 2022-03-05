package com.aditya.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthome.ui.theme.SmartHomeTheme
import com.aditya.smarthome.util.Navigation
import com.aditya.smarthome.util.Screen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHomeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val navController = rememberAnimatedNavController()
                    mainViewModel.userLoggedIn.value?.let { loggedIn ->
                        if (loggedIn) {
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
}

