package com.aditya.smarthome.ui.screens.device_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aditya.smarthome.ui.components.StandardTextField

@Composable
fun DeviceDetailScreen(
    viewModel: DeviceDetailViewModel = hiltViewModel()
) {
    val device = viewModel.device.value
    device?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name")
                Text(device.name)
            }
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Status")
                Switch(
                    checked = device.status != 0,
                    onCheckedChange = {
                        viewModel.updateDeviceStatus(
                            if (device.status == 0) 100 else 0
                        )
                    }
                )
            }
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Edit Device")
                IconButton(
                    onClick = { viewModel.toggleEditDevice() },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Device"
                        )
                    }
                )
            }
            if (viewModel.showEditDevice.value) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextField(
                        text = viewModel.deviceNameText.value,
                        onValueChange = { viewModel.onDeviceNameTextChange(it) }
                    )
                    IconButton(
                        onClick = { viewModel.updateDeviceName() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Device Name"
                        )
                    }
                }
                Divider()
                LazyRow {
                    items(viewModel.allIcons.value) { icon ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        ) {
                            AsyncImage(
                                model = icon,
                                contentDescription = "Device Icons",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            if (device.icon == icon) {
                                Box(
                                    modifier = Modifier.clickable { viewModel.updateDeviceIcon(icon) },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected Icon",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                                .background(Color.Blue.copy(0.5f))
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
            Divider()
        }
    }
}