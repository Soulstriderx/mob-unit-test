package com.fwrdgrp.mob23location.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwrdgrp.mob23location.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    val repo: Repo
) : ViewModel() {
    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    private val _greetings = MutableStateFlow("")
    val greetings = _greetings.asStateFlow()

    fun greet(name: String) {
        viewModelScope.launch {
            delay(1000)
            _greetings.value = "Hello $name"
        }
    }

    fun greetings(): String {
        return "Hello ${fetchUser()}"
    }

    fun login(email: String, password: String) {
        val vRes = validateFields(email, password)
        if (vRes != null) {
            Log.d("debug", "Wrong Credentials")
        }
        viewModelScope.launch(Dispatchers.IO) {
            val user = repo.login(email, password)
            if (user != null) {
                _finish.emit(Unit)
                return@launch
            }
        }
    }

    fun validateFields(email: String, password: String): String? {
        return try {
            require(email.isNotBlank() && email == "email@a.com") { "Invalid Email" }
            require(password.isNotBlank() && password == "pass") { "Wrong Password" }
            null
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    fun fetchUser(): String {
        val user = repo.getUsers()
        _greetings.value = "Hello $user"
        return user
    }
}