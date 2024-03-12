package com.example.willowhealth.utils

private const val MIN_PASS_LENGTH = 6
private const val MIN_PHONE_LENGTH = 10
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
private const val PHONE_PATTERN = "^\\+\\d+$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank()
//            && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH
}

fun String.isValidPhone(): Boolean {
    return this.isNotBlank()
//            &&
//            this.length >= MIN_PHONE_LENGTH &&
//            Pattern.compile(PHONE_PATTERN).matcher(this).matches()
}

