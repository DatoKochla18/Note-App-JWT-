package com.example.notesapp.feature.login.presentation.screen

import android.util.Log
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
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.R
import com.example.notesapp.core.domain.util.error.EmailError
import com.example.notesapp.core.domain.util.error.PasswordError
import com.example.notesapp.core.presentation.components.CustomErrorTextField
import com.example.notesapp.core.presentation.components.CustomPasswordField
import com.example.notesapp.core.presentation.extension.CollectAsUiEvents
import com.example.notesapp.core.presentation.extension.asStringResource
import com.example.notesapp.core.presentation.resource.Colors
import com.example.notesapp.feature.login.presentation.components.SocialLoginButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    val uiState by viewModel
        .uiState
        .collectAsStateWithLifecycle()

    LoginScreen(
        uiState,
        onEvent = viewModel::onEvent,
        uiEvents = viewModel.uiEvents,
        navigateToRegisterScreen = navigateToRegisterScreen,
        navigateToHomeScreen = navigateToHomeScreen,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    uiEvents: Flow<LoginSideEffect>,
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Log.d("uistate", uiState.toString())
    val context = LocalContext.current

    uiEvents.CollectAsUiEvents { event ->
        when (event) {
            is LoginSideEffect.ShowNetworkError -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(event.error.asStringResource()),
                    duration = SnackbarDuration.Short
                )
            }

            LoginSideEffect.SuccessFullLogin -> {
                navigateToHomeScreen()
            }
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
                        imageVector = Icons.Default.Lock,
                        contentDescription = "App Logo",
                        tint = Colors.CYAN,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.welcome_back),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Colors.BLACK
                )

                Text(
                    text = stringResource(R.string.sign_in_to_continue),
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
                        OutlinedTextField(
                            value = uiState.email,
                            onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
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

                        CustomPasswordField(
                            currentValue = uiState.password,
                            label = stringResource(R.string.password),
                            isError = uiState.passwordError != null,
                            toShowPassword = uiState.showPassword,
                            onShowPasswordChanged = { onEvent(LoginEvent.SwitchShowPasswordStatus) }
                        ) { onEvent(LoginEvent.OnPasswordChanged(it)) }

                        if (uiState.passwordError != null) {
                            CustomErrorTextField(
                                stringResource(uiState.passwordError.asStringResource()),
                                Modifier
                                    .align(Alignment.Start)
                                    .padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Remember Me
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.remember_me),
                                color = Colors.BLACK.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                            Checkbox(
                                checked = uiState.rememberMe,
                                onCheckedChange = { onEvent(LoginEvent.SwitchCheckBoxStatus) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Colors.CYAN,
                                    uncheckedColor = Colors.GRAY
                                )
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = context.getString(R.string.forgot_password),
                                color = Colors.CYAN,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.clickable { }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = { onEvent(LoginEvent.Login(uiState.email, uiState.password)) },
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
                        text = stringResource(R.string.login),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Social Login Options
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.or_sign_in_with),
                        color = Colors.GRAY,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SocialLoginButton(
                            icon = painterResource(R.drawable.ic_google),
                            backgroundColor = Color(0xFFEA4335).copy(alpha = 0.1f),
                            contentColor = Color(0xFFEA4335),
                            onClick = { /* Handle Google login */ }
                        )

                        SocialLoginButton(
                            icon = painterResource(id = R.drawable.ic_facebook),
                            backgroundColor = Color(0xFF1877F2).copy(alpha = 0.1f),
                            contentColor = Color(0xFF1877F2),
                            onClick = { /* Facebook */ }
                        )

                        SocialLoginButton(
                            icon = painterResource(R.drawable.ic_twitter), // Replace with Twitter/X icon
                            backgroundColor = Color(0xFF000000).copy(alpha = 0.1f),
                            contentColor = Color(0xFF000000),
                            onClick = {  }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Sign Up Text
                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.don_t_have_account),
                        color = Colors.BLACK.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.sign_up),
                        color = Colors.CYAN,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navigateToRegisterScreen()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(
            showPassword = true,
            isLoading = false,
            isValidForm = true,
            emailError = EmailError.INVALID_EMAIL,
            passwordError = PasswordError.SHORT_PASSWORD,
            password = "sakdl",
            rememberMe = true
        ),
        onEvent = {}, navigateToRegisterScreen = {},
        uiEvents = flow { }, navigateToHomeScreen = {}, snackBarHostState = SnackbarHostState()
    )
}

