package com.fwrdgrp.mob23location.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.fwrdgrp.mob23location.ui.composables.CustomTextField
import com.fwrdgrp.mob23location.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.finish.collect {
            navController.navigate(Screen.Home) {
                popUpTo(Screen.Login)
            }
        }
    }
    Login({ email, pass ->
        viewModel.login(email, pass)
    }) { navController.navigate(Screen.Register) }
}

@Composable
fun Login(
    onLoginClick: (String, String) -> Unit,
    navToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var vis by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Login", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(1.dp))
            CustomTextField("Email", email) { email = it }
            CustomTextField(
                "Password", password, true,
                vis, { vis = it })
            { password = it }
            Button(onClick = { onLoginClick(email, password) }) {
                Text("Login")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?")
                TextButton(onClick = { navToRegister() }) {
                    Text("Sign-up Here!")
                }
            }
        }
    }
}