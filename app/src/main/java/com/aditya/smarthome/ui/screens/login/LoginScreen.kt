package com.aditya.smarthome.ui.screens.login

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
fun LoginScreen(
    onSuccessfulLogin: () -> Unit,
    onSignupClick: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    loginViewModel.user.value?.let { onSuccessfulLogin() }

    val errorMessage = loginViewModel.errorMessage.value

    if(errorMessage.isNotEmpty()) {
        AlertDialogCard(
            title = "Error",
            text = errorMessage,
            confirmButtonText = "OK",
            onConfirmClick = { loginViewModel.resetErrorMessage() },
            onDialogDismiss = { loginViewModel.resetErrorMessage() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_smart_home),
            contentDescription = "Smart Home",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(24.dp)
                .size(150.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = loginViewModel.emailText.value,
                onValueChange = { loginViewModel.setEmailText(it) },
                hint = stringResource(id = R.string.email),
                maxLength = 30,
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                isError = loginViewModel.emailTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            StandardTextField(
                text = loginViewModel.passwordText.value,
                onValueChange = { loginViewModel.setPasswordText(it) },
                hint = stringResource(id = R.string.password),
                maxLength = 30,
                leadingIcon = Icons.Filled.Lock,
                keyboardType = KeyboardType.Password,
                isError = loginViewModel.passwordTextError.value
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    loginViewModel.loginUser()
                },
                modifier = Modifier
                    .align(Alignment.End),
                enabled = !loginViewModel.showProgressBar.value
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        if (loginViewModel.showProgressBar.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
                .clickable { onSignupClick() },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.do_not_have_an_account))
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append(stringResource(id = R.string.sign_up))
                }
            },
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )

    }
}
