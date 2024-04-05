package com.mobile.wallet.domain.login

import com.mobile.wallet.TestDispatcherProvider
import com.mobile.wallet.data.core.DispatcherProvider
import com.mobile.wallet.data.repository.auth.FakeFirebaseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun initialization() {
        viewModel = LoginViewModel(FakeFirebaseRepository(true), dispatcherProvider)

        val uiState = viewModel.uiState.value

        assertTrue(uiState.email.isEmpty())
        assertTrue(uiState.password.isEmpty())
    }

    @Test
    fun testLoginSuccess() = runTest(dispatcherProvider.io) {

        viewModel = LoginViewModel(FakeFirebaseRepository(true), dispatcherProvider)

        viewModel.onEvent(LoginUIEvent.LoginButtonClicked)

        assertTrue(viewModel.errorMessage.value.isEmpty())
        assertTrue(viewModel.navigate.value)
        assertFalse(viewModel.loginInProgress.value)
    }

    @Test
    fun testLoginError() = runTest(dispatcherProvider.io) {

        viewModel = LoginViewModel(FakeFirebaseRepository(false), dispatcherProvider)

        viewModel.onEvent(LoginUIEvent.LoginButtonClicked)


        assertTrue(viewModel.errorMessage.value.isNotEmpty())
        assertFalse(viewModel.loginInProgress.value)
    }


}