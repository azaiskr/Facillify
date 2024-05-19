package com.lidm.facillify.ui.siswa.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.ui.components.InputTextFieldDefault
import com.lidm.facillify.ui.components.SecondaryButton
import com.lidm.facillify.ui.login.InputBox

@Preview(showBackground = true)
@Composable
fun FormTambahDataOrtu(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        var emailOrtu by rememberSaveable { mutableStateOf("") }

        InputTextFieldDefault(
            topText = "Email Orang Tua",
            insideText = "Masukkan email orang tua",
            valueText = emailOrtu,
            onValueChange = {emailOrtu = it},
            isEmail = true,
        )
        Spacer(modifier = modifier.height(24.dp))
        SecondaryButton(
            modifier = modifier,
            onClick = { /*TODO*/ },
            outline = false,
            label = "Tambah"
        )
    }
}