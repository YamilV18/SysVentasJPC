package pe.upeu.sysventasjpc.ui.presentation.screens.login

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import pe.upeu.sysventasjpc.ui.presentation.components.ErrorImageAuth
import pe.upeu.sysventasjpc.ui.presentation.components.ImageLogin
import pe.upeu.sysventasjpc.ui.presentation.components.ProgressBarLoading

import pe.upeu.sysventasjpc.ui.theme.LightRedColors
import pe.upeu.sysventasjpc.utils.ComposeReal
import pe.upeu.sysventasjpc.utils.TokenUtils

import pe.upeu.sysventasjpc.modelo.UsuarioDto
import pe.upeu.sysventasjpc.ui.presentation.components.form.EmailTextField
import pe.upeu.sysventasjpc.ui.presentation.components.form.LoginButton
import pe.upeu.sysventasjpc.ui.presentation.components.form.PasswordTextField
import pe.upeu.sysventasjpc.ui.theme.SysVentasJPCTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isLogin by viewModel.islogin.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val loginResul by viewModel.listUser.observeAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 6
    val isFormValid = isEmailValid && isPasswordValid

    val loginResult by viewModel.listUser.observeAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLogin()
        Text("Login Screen", fontSize = 40.sp)
        LaunchedEffect(isLogin, loginResult) {
            if (isLogin && loginResult != null) {
                Log.i("TOKENV", TokenUtils.TOKEN_CONTENT)
                loginResult?.let {
                    Log.i("DATA", it.user)
                }
                navigateToHome()
            }
        }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            isError = email.isNotEmpty() && !isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            isError = password.isNotEmpty() && !isPasswordValid,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val user = UsuarioDto(user = email, clave = password)
                viewModel.loginSys(user)
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }

        ComposeReal.COMPOSE_TOP.invoke()
    }

    ErrorImageAuth(isImageValidate = isError)
    ProgressBarLoading(isLoading = isLoading)
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val colors = LightRedColors
    val darkTheme = isSystemInDarkTheme()
    SysVentasJPCTheme(colorScheme = colors) {
        LoginScreen(
            navigateToHome = {}
        )
    }
}