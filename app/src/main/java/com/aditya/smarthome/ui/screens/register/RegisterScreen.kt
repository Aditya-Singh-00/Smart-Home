package com.aditya.smarthome.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smarthome.ui.components.AlertDialogCard
import com.aditya.smarthome.R
import com.aditya.smarthome.ui.components.StandardTextField

@Composable
fun RegisterScreen(
    onSuccessfulRegistration: () -> Unit,
    onLoginClick: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {

    registerViewModel.user.value?.let { onSuccessfulRegistration() }

    val errorMessage = registerViewModel.errorMessage.value

    if (errorMessage.isNotEmpty()) {
        AlertDialogCard(
            title = "Error",
            text = errorMessage,
            confirmButtonText = "OK",
            onConfirmClick = { registerViewModel.resetErrorMessage() },
            onDialogDismiss = { registerViewModel.resetErrorMessage() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = registerViewModel.emailText.value,
                onValueChange = { registerViewModel.setEmailText(it) },
                keyboardType = KeyboardType.Email,
                maxLength = 30,
                leadingIcon = Icons.Filled.Email,
                hint = stringResource(id = R.string.email),
                isError = registerViewModel.emailTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = registerViewModel.nameText.value,
                onValueChange = { registerViewModel.setNameText(it) },
                keyboardType = KeyboardType.Text,
                maxLength = 30,
                leadingIcon = Icons.Filled.Person,
                hint = stringResource(id = R.string.username),
                isError = registerViewModel.nameTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = registerViewModel.passwordText.value,
                onValueChange = { registerViewModel.setPasswordText(it) },
                keyboardType = KeyboardType.Password,
                maxLength = 30,
                leadingIcon = Icons.Filled.Lock,
                hint = stringResource(id = R.string.password),
                isError = registerViewModel.passwordTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    registerViewModel.registerUser()
                },
                modifier = Modifier
                    .align(Alignment.End),
                enabled = !registerViewModel.showProgressBar.value
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        if (registerViewModel.showProgressBar.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .clickable { onLoginClick() },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.already_have_an_account))
                withStyle(style = SpanStyle(color = Green)) {
                    append(stringResource(id = R.string.sign_in))
                }
            },
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}
