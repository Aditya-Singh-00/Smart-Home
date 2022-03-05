package com.aditya.smarthome.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aditya.smarthome.R
import com.aditya.smarthome.data.models.Device

@ExperimentalAnimationApi
@Composable
fun DeviceCard(
    device: Device,
    onDeviceClick: () -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onDeviceClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = device.icon,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = device.name,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Switch(
                checked = device.status != 0,
                onCheckedChange = {
                    onDeviceStatusChange(
                        device.id,
                        if (device.status == 0) 100 else 0
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}