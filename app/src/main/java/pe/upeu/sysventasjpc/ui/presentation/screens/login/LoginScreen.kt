package pe.upeu.sysventasjpc.ui.presentation.screens.login

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val errorMessage by viewModel.errorMessage.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogin()
        Text("Login Screen", fontSize = 40.sp)
        BuildEasyForms { easyForm ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                EmailTextField(easyForms = easyForm, text ="","E-Mail:", "U")
                PasswordTextField(easyForms = easyForm, text ="", label ="Password:" )
                LoginButton(easyForms=easyForm, onClick = {
                    val dataForm=easyForm.formData()
                    val user=UsuarioDto(
                        (dataForm.get(0) as EasyFormsResult.StringResult).value,
                        (dataForm.get(1) as EasyFormsResult.StringResult).value)
                    viewModel.loginSys(user)
                    scope.launch {
                        delay(3600)
                        if(isLogin && loginResul!=null){
                            Log.i("TOKENV", TokenUtils.TOKEN_CONTENT)
                            Log.i("DATA", loginResul!!.user)
                            navigateToHome.invoke()
                        }else{
                            Log.v("ERRORX", "Error logeo")
                            Toast.makeText(context,"Error al conectar",Toast.LENGTH_LONG)
                        }
                    }
                },

                    label = "Log In"
                )
                /*Button(onClick = {
                    navigateToHome.invoke()
                }) {
                    Text("Ir a Detalle")
                }*/
                ComposeReal.COMPOSE_TOP.invoke()
            }
        }
        ErrorImageAuth(isImageValidate = isError)
        ProgressBarLoading(isLoading = isLoading)
    }
    // Mostrar Snackbar manualmente
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.wrapContentHeight(Alignment.Bottom).padding(16.dp),

        )
    // Mostrar el snackbar cuando haya mensaje de error
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearErrorMessage()
        }
    }
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