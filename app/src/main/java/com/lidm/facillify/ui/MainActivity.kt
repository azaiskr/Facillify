package com.lidm.facillify.ui

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.googlefonts.isAvailableOnDevice
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lidm.facillify.navigation.AuthNavigation
import com.lidm.facillify.navigation.GuruNavigation
import com.lidm.facillify.navigation.OrtuNavigation
import com.lidm.facillify.navigation.SiswaNavigation
import com.lidm.facillify.ui.theme.FacillifyTheme
import com.lidm.facillify.ui.theme.provider
import com.lidm.facillify.util.Role
import kotlinx.coroutines.CoroutineExceptionHandler

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSession().observe(this) { preference ->
            if (preference.token == "") {
                setContent {
                    FacillifyTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White
                        ) {
                            AuthNavigation()
                        }
                    }
                }
            } else {
                when(preference.type) {
                    "murid" -> {
                        setContent {
                            FacillifyTheme {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.White
                                ) {
                                    SiswaNavigation()
                                }
                            }
                        }
                    }
                    "guru" -> {
                        setContent {
                            FacillifyTheme {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.White
                                ) {
                                    GuruNavigation(email = preference.email)
                                }
                            }
                        }
                    }
                    "orang tua" -> {
                        setContent {
                            FacillifyTheme {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.White
                                ) {
                                    OrtuNavigation(email = preference.email)
                                }
                            }
                        }
                    }
                }
            }
        }
//                            Role.STUDENT -> {
//                                if (userLogin.isTested == false) {
//                                    GayaBelajarNavigation()
//                                } else {
//                                    SiswaNavigation(
//                                        loginData = userLogin
//                                    )
//                                }
//                            }
//
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val handler = CoroutineExceptionHandler { _, throwable ->
        // process the Throwable
        Log.e(TAG, "There has been an issue: ", throwable)
    }

    CompositionLocalProvider(
        LocalFontFamilyResolver provides createFontFamilyResolver(LocalContext.current, handler)
    ) {
        Column {
            Text(
                text = "Hello World!",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
            )
            Text(
                text = "Hello $name!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier,
            )
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (provider.isAvailableOnDevice(context)) {
            Log.d(TAG, "Success!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FacillifyTheme {
        Greeting("Facillify Coming Soon")
    }
}