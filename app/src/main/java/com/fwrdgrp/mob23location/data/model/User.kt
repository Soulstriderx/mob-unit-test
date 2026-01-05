package com.fwrdgrp.mob23location.data.model

data class User(
    val username: String,
    val email: String,
    val password: String,
    val id: Int? = 0
)
