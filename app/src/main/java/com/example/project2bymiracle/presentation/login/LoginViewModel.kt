package com.example.project2bymiracle.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project2bymiracle.common.ValidationEvents
import com.example.project2bymiracle.domain.models.Validations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getValidations: Validations
): ViewModel() {

    private var _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _passwordIsVisible = mutableStateOf(false)
    val passwordIsVisible: State<Boolean> = _passwordIsVisible

    private val validationEventChannel = Channel<ValidationEvents>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvents) {
        when(event) {
            is LoginEvents.EnteredEmail -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is LoginEvents.EnteredPassword -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginEvents.ClickedLoginButton -> {
                val validEntries = isUserEntriesValid()
                if (validEntries) {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvents.Success)
                    }
                }
            }
            is LoginEvents.ClickedToggleButton -> {
                togglePassword()
            }
        }
    }

    private fun togglePassword() {
       _passwordIsVisible.value = !_passwordIsVisible.value
    }

    /**
     * This function allows you to authenticate a user by vetting their entries.
     * It returns true if the entries are valid, and false if they are invalid.
     */
    private fun isUserEntriesValid(): Boolean {

        val emailResult = getValidations.validateEmail(_state.value.email)
        val passwordResult = getValidations.validatePassword(_state.value.password)

        /**
         * This value checks if there is any error in both fields.
         */
        val errorInEntry = listOf(emailResult, passwordResult).any { !it.successful }

        _state.value = _state.value.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )

        return !errorInEntry
    }
}