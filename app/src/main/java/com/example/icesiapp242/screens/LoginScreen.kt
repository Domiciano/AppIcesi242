package com.example.icesiapp242.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.icesiapp242.domain.model.AuthState
import com.example.icesiapp242.viewmodel.SignupViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: SignupViewModel = viewModel()) {
    val authState by authViewModel.authState.observeAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(value = email, onValueChange = { email = it })
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )

        when(authState){
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthState.Error ->{
                Text(text = (authState as AuthState.Error).message)
            }
            is AuthState.Success ->{
                navController.navigate("profile")
            }
            AuthState.Idle -> {
            }
            null -> {

            }
        }

        Button(onClick = {
            authViewModel.signin(email, password)
        }) {
            Text(text = "Iniciar sesion")
        }
    }
}