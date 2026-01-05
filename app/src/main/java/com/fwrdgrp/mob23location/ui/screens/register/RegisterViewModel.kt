package com.fwrdgrp.mob23location.ui.screens.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwrdgrp.mob23location.data.model.User
import com.fwrdgrp.mob23location.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val repo: Repo
) : ViewModel() {
    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    fun register(username: String, email: String, password: String, password2: String) {
        val validated = validateFields(username, email, password, password2)
        if (!validated) return
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.register(buildUser(username, email, password))
            if (user) {
                Log.d("debug", "User Registered")
                _finish.emit(Unit)
                return@launch
            }
            Log.d("debug", "Duplicate Email")
        }
    }

    private fun validateFields(
        username: String,
        email: String,
        password: String,
        password2: String
    ): Boolean {
        if (password.isBlank()) {
            Log.d("debug", "Password cannot be blank")
            return false
        } else if (password2.isBlank()) {
            Log.d("debug", "Confirm Password cannot be blank")
            return false
        } else if (password != password2) {
            Log.d("debug", "Password and Confirm Password must match")
            return false
        } else if (username.isBlank()) {
            Log.d("debug", "Username cannot be blank")
            return false
        } else if (email.isBlank()) {
            Log.d("debug", "email cannot be blank")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d("debug", "Wrong email format")
            return false
        }
        return true
    }

    private fun buildUser(username: String, email: String, password: String): User {
        return User(username, email, password)
    }
}