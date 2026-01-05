package com.fwrdgrp.mob23location.data.repo

import com.fwrdgrp.mob23location.data.model.User

interface Repo {
    fun getUsers() : String
    suspend fun login(email: String, password: String): User?
    suspend fun register(user: User): Boolean
}