package com.fwrdgrp.mob23location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fwrdgrp.mob23location.service.LocationService
import com.fwrdgrp.mob23location.ui.navigation.AppNav
import com.fwrdgrp.mob23location.ui.theme.Mob23LocationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Log.d("debug", "Permission Granted")
        } else {
            Log.d("debug", "Permissions Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mob23LocationTheme {
                ComposeApp()
            }
        }
        checkPermissionAndStart()
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.all { it.value }) {
            startTracking()
            requestNotificationPermission()
            checkBGPermissionsAndStart()
        }
    }

    private val bgPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            requestNotificationPermission()
            startTracking()
        }
    }


    // all and any functions

    private fun checkPermissionAndStart() {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val missing = perms.filter {
            ContextCompat.checkSelfPermission(
                this, it
            ) != PackageManager.PERMISSION_GRANTED
        }



        if (missing.isNotEmpty()) {
            locationPermissionLauncher.launch(missing.toTypedArray())
        } else {
            startTracking()
            requestNotificationPermission()
            checkBGPermissionsAndStart()
        }
    }

    private fun checkBGPermissionsAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                bgPermissionLauncher.launch(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }
        }
    }

    private fun startTracking() {
        ContextCompat.startForegroundService(
            this,
            Intent(this, LocationService::class.java)
        )
    }

    private fun stopTracking() {
        stopService(Intent(this, LocationService::class.java))
    }

    fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun ComposeApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AppNav()
        }
    }
}