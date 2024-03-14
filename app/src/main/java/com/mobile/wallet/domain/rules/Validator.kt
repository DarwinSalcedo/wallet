package com.mobile.wallet.domain.rules

object Validator {

    fun validateName(value: String): ValidationResult {
        return ValidationResult(
            (value.isNotEmpty() && value.length >= 2)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        val emailRegex = Regex(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7e]|\\\\(?:[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f]|[a-f0-9]{2}))*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z]{2,}|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9]{2})\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9]{2})\\])"
        )
        return ValidationResult(
            emailRegex.matches(email)
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 4)
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)








