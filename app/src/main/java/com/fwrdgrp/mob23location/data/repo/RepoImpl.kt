package com.fwrdgrp.mob23location.data.repo

import android.util.Log
import com.fwrdgrp.mob23location.data.model.User

class RepoImpl : Repo {
    val fakeDatabase = mutableMapOf<Int, User>(
        1 to User("Alex", "email@a.com", "pass", 1)
    )

    override fun getUsers(): String {
        return "Hello User"
    }

    override suspend fun login(email: String, password: String): User? {
        return fakeDatabase.values.firstOrNull {
            it.email == email && it.password == password
        }
    }

    override suspend fun register(user: User): Boolean {
        val dupe = dupeCheck(user)
        if (dupe) return false

        val newId = ++count
        fakeDatabase[newId] = user.copy(id = newId)
        Log.d("debug", fakeDatabase.toString())
        return true
    }

    fun dupeCheck(user: User): Boolean {
        return fakeDatabase.values.any {
            it.email == user.email
        }
    }

    companion object {
        var count = 0
    }
}