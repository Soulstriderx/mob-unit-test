package com.fwrdgrp.mob23location.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fwrdgrp.mob23location.ui.screens.home.HomeScreen
import com.fwrdgrp.mob23location.ui.screens.login.LoginScreen
import com.fwrdgrp.mob23location.ui.screens.register.RegisterScreen

@Composable
fun AppNav(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login, modifier = modifier) {
        composable<Screen.Home> { HomeScreen(navController) }
        composable<Screen.Login> { LoginScreen(navController) }
        composable<Screen.Register> { RegisterScreen(navController) }
    }
}