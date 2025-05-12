package com.example.notesapp.feature.register.presentation.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.core.presentation.components.CustomErrorTextField
import com.example.notesapp.core.presentation.components.CustomPasswordField
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.extension.asStringResource
import com.example.notesapp.core.presentation.resource.Colors
import com.example.notesapp.feature.register.presentation.extension.asStringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterRootScreen(
    viewModel: RegisterViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState,
    navigateToLoginScreen: () -> Unit,

    ) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterScreen(
        uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        snackBarHostState = snackBarHostState,
        navigateToLoginScreen = navigateToLoginScreen
    )
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    uiEvents: Flow<RegisterSideEffect>,
    onEvent: (RegisterEvent) -> Unit,
    navigateToLoginScreen: () -> Unit,
    snackBarHostState: SnackbarHostState,

    ) {
    val context = LocalContext.current

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is RegisterSideEffect.NavigateToLoginScreen -> navigateToLoginScreen()

            is RegisterSideEffect.ShowError -> snackBarHostState.showSnackbar(
                message = context.getString(event.error.asStringResource()),
                duration = SnackbarDuration.Short
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F5F5),
                        Color(0xFFE0F7FA)
                    )
                )
            )
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Colors.CYAN,
                    strokeWidth = 3.dp
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Colors.CYAN.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Register Icon",
                        tint = Colors.CYAN,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.create_account),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Colors.BLACK
                )

                Text(
                    text = stringResource(R.string.fill_details_to_register),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Colors.BLACK.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Colors.WHITE),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Email Field
                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { onEvent(RegisterEvent.OnEmailChanged(it)) },
                            label = { Text(stringResource(id = R.string.email)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            isError = uiState.emailError != null,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Colors.CYAN,
                                unfocusedBorderColor = Colors.GRAY,
                                focusedTextColor = Colors.BLACK
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = if (uiState.emailError != null) Colors.RED else Colors.CYAN
                                )
                            },
                            shape = RoundedCornerShape(12.dp)
                        )

                        if (uiState.emailError != null) {
                            CustomErrorTextField(
                                stringResource(uiState.emailError.asStringResource()),
                                Modifier
                                    .align(Alignment.Start)
                                    .padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password Field
                        CustomPasswordField(
                            currentValue = uiState.password,
                            label = stringResource(R.string.password),
                            isError = uiState.passwordError != null,
                            toShowPassword = uiState.showPassword,
                            onShowPasswordChanged = { onEvent(RegisterEvent.OnShowPasswordChanged) }
                        ) { onEvent(RegisterEvent.OnPasswordChanged(it)) }

                        if (uiState.passwordError != null) {
                            CustomErrorTextField(
                                stringResource(uiState.passwordError.asStringResource()),
                                Modifier
                                    .align(Alignment.Start)
                                    .padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Repeat Password Field
                        CustomPasswordField(
                            currentValue = uiState.repeatPassword,
                            label = stringResource(R.string.repeat_password),
                            isError = uiState.repeatedPasswordError != null,
                            toShowPassword = uiState.showPassword,
                            onShowPasswordChanged = { onEvent(RegisterEvent.OnShowPasswordChanged) }
                        ) { onEvent(RegisterEvent.OnRepeatedPasswordChanged(it)) }

                        if (uiState.repeatedPasswordError != null) {
                            CustomErrorTextField(
                                stringResource(uiState.repeatedPasswordError.asStringResource()),
                                Modifier
                                    .align(Alignment.Start)
                                    .padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = uiState.acceptedTerms,
                                onCheckedChange = { onEvent(RegisterEvent.OnTermsAcceptedChanged) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Colors.CYAN,
                                    uncheckedColor = Colors.GRAY
                                )
                            )

                            Text(
                                text = stringResource(R.string.accept_terms),
                                color = Colors.BLACK.copy(alpha = 0.7f),
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            Text(
                                text = stringResource(R.string.terms_and_conditions),
                                color = Colors.CYAN,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable { /* Handle terms click */ }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Register Button
                Button(
                    onClick = { onEvent(RegisterEvent.Register(uiState.email, uiState.password)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                    enabled = uiState.isValidForm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isValidForm) Colors.CYAN else Colors.CYAN.copy(
                            alpha = 0.5f
                        ),
                        contentColor = Colors.WHITE
                    ),
                    shape = RoundedCornerShape(28.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        color = Colors.BLACK.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.login),
                        color = Colors.CYAN,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navigateToLoginScreen()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(uiState = RegisterUiState(isLoading = false),
        onEvent = {},
        uiEvents = flow { },
        snackBarHostState = SnackbarHostState(),
        navigateToLoginScreen = { })
}