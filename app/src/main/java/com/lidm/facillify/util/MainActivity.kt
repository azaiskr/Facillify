package com.lidm.facillify.util

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.googlefonts.isAvailableOnDevice
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lidm.facillify.ui.theme.FacillifyTheme
import com.lidm.facillify.ui.theme.provider
import kotlinx.coroutines.CoroutineExceptionHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FacillifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Facillify Coming Soon")
                }
            }
        }
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