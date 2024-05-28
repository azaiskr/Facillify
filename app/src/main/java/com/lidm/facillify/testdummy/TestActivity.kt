package com.lidm.facillify.testdummy

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val examViewModel: ExamViewModel = viewModel()
            MainScreen(viewModel = examViewModel)
        }
    }
}

@Suppress("DEPRECATION")
class ExamViewModel : ViewModel() {
    var isExamFinished by mutableStateOf(false)
        private set

    var isBackDisabled by mutableStateOf(false)
        private set

    var isKioskModeEnabled by mutableStateOf(false)
        private set

    fun finishExam() {
        isExamFinished = true
    }

    fun enableBackButton() {
        isBackDisabled = false
    }

    fun disableBackButton() {
        isBackDisabled = true
    }

    fun startLockTask(context: Context) {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (!activityManager.isInLockTaskMode) {
            (context as? Activity)?.startLockTask()
            isKioskModeEnabled = true
        }
    }

    fun stopLockTask(context: Context) {
        if (isExamFinished) {
            (context as? Activity)?.stopLockTask()
            isKioskModeEnabled = false
        }
    }
}

@Composable
fun MainScreen(viewModel: ExamViewModel) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        ExamScreen(viewModel = viewModel)

        Button(
            onClick = { viewModel.disableBackButton() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Disable Back Button")
        }

        Button(
            onClick = { viewModel.enableBackButton() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Enable Back Button")
        }

        Button(
            onClick = { viewModel.startLockTask(context) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Enable Kiosk Mode")
        }

        Button(
            onClick = {
                if (viewModel.isExamFinished) {
                    viewModel.stopLockTask(context)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Disable Kiosk Mode")
        }
    }
}

@Composable
fun ExamScreen(viewModel: ExamViewModel) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val context = LocalContext.current

    DisposableEffect(viewModel.isBackDisabled) {
        val callback = object : OnBackPressedCallback(viewModel.isBackDisabled) {
            override fun handleOnBackPressed() {
                // Tidak melakukan apapun saat tombol back ditekan
            }
        }
        onBackPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    // UI dan logika untuk soal ujian
    Column {
        Text("This is the exam screen.")

        Button(onClick = {
            // Logika ketika jawaban dikirim
            viewModel.finishExam()
            if (viewModel.isKioskModeEnabled) {
                viewModel.stopLockTask(context)
            }
        }) {
            Text("Kirim Jawaban")
        }
    }
}
