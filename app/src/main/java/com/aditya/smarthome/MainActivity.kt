package com.aditya.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aditya.smarthome.ui.components.AlertDialogCard
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
                val mainViewModel: MainViewModel = hiltViewModel()
                val navController = rememberAnimatedNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            modifier = Modifier.fillMaxWidth(),
                            actions = {
                                if (navBackStackEntry?.destination?.route == Screen.HomeScreen.route) {
                                    IconButton(
                                        onClick = { mainViewModel.showLogoutAlert() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Logout,
                                            contentDescription = "Logout Button",
                                            tint = MaterialTheme.colors.onBackground
                                        )
                                    }
                                }
                            },
                            backgroundColor = MaterialTheme.colors.primary.copy(0.4f)
                        )
                    },
                    scaffoldState = scaffoldState,
                    snackbarHost = {
                        SnackbarHost(it) { data ->
                            Snackbar(
                                backgroundColor = MaterialTheme.colors.primary,
                                actionColor = MaterialTheme.colors.onBackground,
                                snackbarData = data
                            )
                        }
                    }
                ) {
                    mainViewModel.userLoggedIn.value?.let { loggedIn ->
                        if (loggedIn) {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.HomeScreen.route,
                                scaffoldState = scaffoldState
                            )
                        } else {
                            Navigation(
                                navController = navController,
                                startDestination = Screen.LoginScreen.route,
                                scaffoldState = scaffoldState
                            )
                        }
                    }
                    if (mainViewModel.logoutIconClicked.value) {
                        AlertDialogCard(
                            title = "Logout",
                            text = "Do you want to logout?",
                            confirmButtonText = "Yes",
                            dismissButtonText = "No",
                            onConfirmClick = {
                                mainViewModel.logout()
                                navController.navigate(Screen.LoginScreen.route) {
                                    popUpTo(Screen.HomeScreen.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            onDialogDismiss = { mainViewModel.dismissLogoutAlert() }
                        )
                    }
                }
            }
        }
    }
}

