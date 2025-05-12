package com.example.notesapp.core.presentation.extension

import com.example.notesapp.R
import com.example.notesapp.core.domain.util.error.EmailError
import com.example.notesapp.core.domain.util.error.NetworkError
import com.example.notesapp.core.domain.util.error.PasswordError

fun PasswordError.asStringResource(): Int {
    return when (this) {
        PasswordError.SHORT_PASSWORD -> R.string.the_password_needs_to_consist_of_at_least_8_characters
        PasswordError.INVALID_PASSWORD -> R.string.the_password_needs_to_contain_at_least_one_letter_and_digit
    }
}

fun EmailError.asStringResource(): Int {
    return when (this) {
        EmailError.INVALID_EMAIL -> R.string.invalid_email
        EmailError.BLANK_FIELD -> R.string.field_can_not_be_blank
    }
}


fun NetworkError.asStringResource(): Int {
    return when (this) {
        NetworkError.ConnectionError ->
            R.string.connection_problem_try_again

        NetworkError.EmptyResponse ->
            R.string.empty_response_fire_backend

        is NetworkError.HttpError ->
            R.string.http_error

        NetworkError.InvalidCredentials ->
            R.string.invalid_credentials

        is NetworkError.ServerError ->
            R.string.server_error

        NetworkError.UserNotFound ->
            R.string.user_not_found
    }
}