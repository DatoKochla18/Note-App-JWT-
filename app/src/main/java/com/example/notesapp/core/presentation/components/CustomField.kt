package com.example.notesapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.notesapp.R
import com.example.notesapp.core.presentation.resource.Colors

@Composable
fun CustomPasswordField(
    currentValue: String,
    label: String,
    isError: Boolean = false,
    toShowPassword: Boolean = false,
    onShowPasswordChanged: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = currentValue,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (toShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password",
                tint = if (isError) Colors.RED else Colors.CYAN
            )
        },
        trailingIcon = {
            val icon =
                if (toShowPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { onShowPasswordChanged() }) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.toggle_password_visibility),
                    tint = Colors.GRAY
                )
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Colors.CYAN,
            unfocusedBorderColor = Colors.GRAY,
            focusedTextColor = Colors.BLACK
        ),
        shape = RoundedCornerShape(12.dp)
    )
}