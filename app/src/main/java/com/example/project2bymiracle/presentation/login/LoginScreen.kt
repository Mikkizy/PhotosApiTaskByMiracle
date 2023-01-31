package com.example.project2bymiracle.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.project2bymiracle.R
import com.example.project2bymiracle.common.Dimensions
import com.example.project2bymiracle.common.ValidationEvents
import com.example.project2bymiracle.presentation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = remember {
        viewModel.state
    }.collectAsState()

    val context = LocalContext.current

    val passwordIsVisible by remember {
       viewModel.passwordIsVisible
    }

    val passwordVisibilityIcon = if (passwordIsVisible) {
        painterResource(id = R.drawable.ic_visibility)
    } else {
        painterResource(id = R.drawable.ic_visibility_off)
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collectLatest { event ->
            when (event) {
                is ValidationEvents.Success -> {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_LONG
                    ).show()

                    //Navigate to photos screen
                    navController.navigate(Screen.PhotosScreen.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimensions.pagePadding)
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(Dimensions.oneSpacer))
        Text(
            text = stringResource(id = R.string.details),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(Dimensions.twoSpacers))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.email,
            onValueChange = {
                viewModel.onEvent(LoginEvents.EnteredEmail(it))
            },
            isError = state.value.emailError != null,
            placeholder = {
                Text(text = "Email or Username")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if (state.value.emailError != null) {
            Text(
                text = state.value.emailError ?: "",
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(Dimensions.oneSpacer))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.value.password,
            onValueChange = {
                viewModel.onEvent(LoginEvents.EnteredPassword(it))
            },
            isError = state.value.passwordError != null,
            placeholder = {
                Text(text = "Password")
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.onEvent(LoginEvents.ClickedToggleButton) }) {
                    Icon(
                        modifier = Modifier
                            .padding(end = Dimensions.pagePadding.div(2))
                            .size(Dimensions.mediumIcon),
                        painter = passwordVisibilityIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            visualTransformation = if (passwordIsVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
        )
        if (state.value.passwordError != null) {
            Text(
                text = state.value.passwordError ?: "",
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(Dimensions.threeSpacers))
        Spacer(modifier = Modifier.height(Dimensions.threeSpacers))
        Text(
            text = stringResource(id = R.string.sponsor),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Dimensions.oneSpacer))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
            ,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = colorResource(id = R.color.kinetic_color),
                contentColor = Color.White
            ),
            onClick = { viewModel.onEvent(LoginEvents.ClickedLoginButton) }
        ) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}