package com.example.icesiapp242

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.icesiapp242.domain.model.User
import com.example.icesiapp242.ui.theme.IcesiAPP242Theme
import com.example.icesiapp242.util.CredentialManagerHelper
import com.example.icesiapp242.viewmodel.SignupViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IcesiAPP242Theme {
                SignupScreen()
            }
        }
    }
}

@Composable
fun SignupScreen(signupViewModel: SignupViewModel = viewModel()) {

    val authState by signupViewModel.authState.observeAsState()
    var context =  LocalContext.current


    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(value = name, onValueChange = { name = it })
            TextField(value = username, onValueChange = { username = it })
            TextField(value = email, onValueChange = { email = it })
            TextField(value = password, onValueChange = { password = it })
            if(authState == 1){
                CircularProgressIndicator()
            }else if(authState == 2){
                Text("Hubo un error", color = Color.Red)
            }
            Button(onClick = {
                signupViewModel.signup(
                    User("", name, username, email),
                    password
                )
            }) {
                Text(text = "Registrarse")
            }

            Button(onClick = {
                signupViewModel.signupWithGoogle(
                    CredentialManagerHelper(context)
                )
            }) {
                Text(text = "Iniciar sesión con Google")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IcesiAPP242Theme {
        Greeting("Android")
    }
}