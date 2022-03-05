package com.aditya.smarthome.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthome.ui.components.AlertDialogCard
import com.aditya.smarthome.ui.components.DeviceCard
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    onDeviceClick: (Int) -> Unit,
    onLogout: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val devices = homeViewModel.devices.value
    val username = homeViewModel.username.value
    val logoutClicked = homeViewModel.logoutIconClicked.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome $username",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground
        )

        if (devices.isNotEmpty()) {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                items(items = devices) { device ->
                    DeviceCard(
                        device = device,
                        onDeviceClick = { onDeviceClick(device.id) },
                        onDeviceStatusChange = { deviceId, status ->
                            homeViewModel.updateStatus(deviceId, status)
                        }
                    )
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )
        }
        Column {
            if (logoutClicked) {
                AlertDialogCard(
                    title = "Logout",
                    text = "Do you want to logout?",
                    confirmButtonText = "Confirm",
                    dismissButtonText = "Dismiss",
                    onConfirmClick = {
                        homeViewModel.logout()
                        onLogout()
                    },
                    onDialogDismiss = { homeViewModel.dismissLogoutDialog() }
                )
            }
        }
    }
}


