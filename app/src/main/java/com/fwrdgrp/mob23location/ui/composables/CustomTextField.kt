package com.fwrdgrp.mob23location.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    label: String,
    value: String,
    isPassword: Boolean = false,
    vis: Boolean = false,
    visChange: (Boolean) -> Unit = {},
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        visualTransformation =
            if (isPassword && !vis) PasswordVisualTransformation()
            else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                Icon(
                    imageVector =
                        if (!vis) Icons.Filled.VisibilityOff
                        else Icons.Filled.Visibility,
                    "",
                    modifier = Modifier.clickable(onClick = { visChange(!vis) })
                )
            }
        }
    )
}