package com.aditya.smarthome.ui.screens.device_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthome.ui.components.StandardTextField

@Composable
fun DeviceDetailScreen(
    viewModel: DeviceDetailViewModel = hiltViewModel()
) {
    val device = viewModel.device.value
    device?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    device.name,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Edit Device",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                IconButton(
                    onClick = { viewModel.toggleEditDevice() },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Device",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                )
            }
            if (viewModel.showEditDevice.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextField(
                        text = viewModel.deviceNameText.value,
                        modifier = Modifier.fillMaxWidth(0.80f),
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
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    itemsIndexed(viewModel.allIcons.value) { index,icon ->
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable { viewModel.updateDeviceIcon(index+1) }
                        ) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = "Device Icons",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(16.dp),
                                contentScale = ContentScale.Fit
                            )
                            if (device.icon == index+1) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(100.dp)
                                        .background(MaterialTheme.colors.primary.copy(0.5f)),
                                    content = {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected Icon",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(16.dp)
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