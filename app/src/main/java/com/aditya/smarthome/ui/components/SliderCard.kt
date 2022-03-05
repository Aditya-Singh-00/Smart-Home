package com.aditya.smarthome.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.aditya.smarthome.data.models.Device

@Composable
fun SliderCard(
    device: Device,
    onDialogDismiss: () -> Unit,
    onValueChange: (Int, Int) -> Unit
) {

//    val color by animateColorAsState(
//        if (device.status == 0) {
//            MaterialTheme.colors.background
//        } else {
//            MaterialTheme.colors.primary
//        }
//    )
//
//    val text = if (device.status == 0) "OFF" else "ON"
//
//    val sliderValue = remember { mutableStateOf(device.status) }
//
//    val vector = device.vector
//
//    val showSlider = vector == icons[2] || vector == icons[6] || vector == icons[7]
//
//    Dialog(onDismissRequest = { onDialogDismiss() }) {
//        Card(
//            elevation = 10.dp,
//            shape = RoundedCornerShape(32.dp),
//            modifier = Modifier
//                .padding(8.dp)
//                .width(200.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(MaterialTheme.colors.surface),
//                verticalArrangement = Arrangement.SpaceEvenly,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = device.vector),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(80.dp)
//                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
//                    contentScale = ContentScale.Fit
//                )
//                Text(
//                    text = device.name,
//                    style = MaterialTheme.typography.body1
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                if (showSlider) {
//                    Slider(
//                        value = sliderValue.value.toFloat(),
//                        onValueChange = {
//                            sliderValue.value = it.toInt()
//                            onValueChange(device.id, it.toInt())
//                        },
//                        valueRange = 0f..100f,
//                        modifier = Modifier.padding(horizontal = 16.dp)
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                } else {
//                    Card(
//                        elevation = 5.dp,
//                        shape = CircleShape,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .width(80.dp)
//                            .clickable(
//                                onClick = {
//                                    if (device.status == OFF) {
//                                        onValueChange(device.id, ON)
//                                    } else {
//                                        onValueChange(device.id, OFF)
//                                    }
//                                }
//                            )
//                    ) {
//                        Box(
//                            modifier = Modifier.background(color)
//                        ) {
//                            Text(
//                                text = text,
//                                style = MaterialTheme.typography.body1,
//                                modifier = Modifier
//                                    .padding(8.dp)
//                                    .align(Alignment.Center)
//                            )
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//    }
}