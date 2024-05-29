package com.lidm.facillify.ui.siswa

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.SecondaryButton
import com.lidm.facillify.ui.viewmodel.UpdateParentEmailViewModel
import com.lidm.facillify.util.ResponseState

@Preview(showBackground = true)
@Composable
fun FormEditEmailOrtu(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val viewModel: UpdateParentEmailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context = LocalContext.current)
    )
    val preferences by viewModel.getSession().observeAsState()
    val studentEmail = preferences?.email.toString()
    var emailOrtu by rememberSaveable { mutableStateOf("") }
    val updateResponse = viewModel.updateEmailResponse.collectAsState()

    when(updateResponse.value){
        is ResponseState.Loading -> {}
        is ResponseState.Success -> {
            onClick()
        }
        is ResponseState.Error -> {
            Log.e("UpdateParentEmail Error", updateResponse.value.toString())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        InputTextFieldDefault(
            topText = "Email Orang Tua",
            insideText = "Masukkan email baru orang tua",
            valueText = emailOrtu,
            onValueChange = { emailOrtu = it },
            isEmail = true,
        )
        Spacer(modifier = modifier.height(24.dp))
        SecondaryButton(
            modifier = modifier,
            onClick = { viewModel.updateParentEmail(studentEmail,emailOrtu) },
            outline = false,
            label = "Simpan"
        )
    }
}