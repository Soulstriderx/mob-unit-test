package com.fwrdgrp.mob23location.ui.screens.login

import com.fwrdgrp.mob23location.data.repo.Repo
import com.fwrdgrp.mob23location.data.repo.RepoImplTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    val repo: Repo = mock()
    val viewModel = LoginViewModel(repo)

    @Before
    fun setup() {
        val testDispatcher = UnconfinedTestDispatcher()
        //Don't use Dispatchers.Main
        Dispatchers.setMain(testDispatcher)

        whenever(repo.getUsers()).thenReturn("Alex")
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetch user returns the mock value`() {
        assertEquals("Alex", viewModel.fetchUser())
    }

    @Test
    fun `greetings should return Hello mock value`() {
        assertEquals("Hello Alex", viewModel.greetings())
    }

    @Test
    fun `fetchUser should update the greetings stateFlow with greetings`() = runTest {
        viewModel.fetchUser()
        val greetings = viewModel.greetings.first()
        assertEquals("Hello Alex", greetings)
    }

//    @Test
//    fun `login should pass when email@a,com and pass`() = runTest {
//        viewModel2.login("email@a.com", "pass")
//        val emit = viewModel2.finish.first()
//        assertEquals(Unit, emit)
//    }

    @Test
    fun `greet should update the greetings stateflow with Hello $name`() = runTest {
        viewModel.greet("Alex")
        val msg = viewModel.greetings.drop(1).first()
        assertEquals("Hello Alex", msg, "Extra info about test")
    }

    @Test
    fun `Validation should fail for email and pass`() {
        assert(viewModel.validateFields("email", "pass") != null)
    }

    @Test
    fun `Validation should fail for email@a,com and password`() {
        assert(viewModel.validateFields("email@a.com", "password") != null)
    }

    @Test
    fun `Validation should pass for email@a,com and pass`() {
        assert(viewModel.validateFields("email@a.com", "pass") == null)
    }

    @Test
    fun `(Should fail)Validation should pass for email@gmail,com and pass`() {
        assert(viewModel.validateFields("email@gmail.com", "pass") == null)
    }
}