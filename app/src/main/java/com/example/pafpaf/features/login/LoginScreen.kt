package com.example.pafpaf.features.login

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import kotlinx.serialization.Serializable

@Serializable
object LoginScreenNav

@Composable
fun LoginScreen(
    onLoginSucceed: () -> Unit
) {
    LoginScreenContent(
        onLoginSucceed = { onLoginSucceed.invoke() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreenContent(
    onLoginSucceed: () -> Unit
) {
    val activity = LocalContext.current as FragmentActivity
    val promptManager by lazy {
        BiometricPromptManager(
            activity
        )
    }
    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )

    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }


    //COMPOSABLE HERE
    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {},
                modifier = Modifier.alpha(0f)
            ) {
                Box(modifier = Modifier
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            promptManager.showBiometricPrompt(
                                title = "Sample prompt",
                                description = "Sample prompt description"
                            )
                        }
                    )
                    .alpha(0f))
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }

    biometricResult?.let { result ->
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                result.error
            }

            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                "Authentication failed"
            }

            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                "Authentication not set"
            }

            BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                onLoginSucceed.invoke()
            }

            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                "Feature unavailable"
            }

            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                "Hardware unavailable"
            }
        }


    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        onLoginSucceed = {},
    )
}