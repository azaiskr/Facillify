package com.lidm.facillify.ui.tracking

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.R
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.StudentCard
import com.lidm.facillify.ui.viewmodel.TrackingAnakViewModel
import com.lidm.facillify.util.ResponseState
import com.lidm.facillify.util.Role

@Composable
fun TrackingAnakScreen(
    onDetailClick: (String) -> Unit,
    role: Role,
    context: Context = LocalContext.current,
) {
    //viewmodel
    val trackingViewModel: TrackingAnakViewModel = viewModel(
        factory = ViewModelFactory.getInstance(context.applicationContext)
    )

    //state
    val listStudentState by trackingViewModel.listStudent.collectAsState()

    //launchedeffect
    LaunchedEffect(role == Role.TEACHER) {
        trackingViewModel.getAllStudent()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(role == Role.TEACHER) {
            when (listStudentState) {
                is ResponseState.Loading -> {
                    //Loading
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ResponseState.Error -> {
                    //Error
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${(listStudentState as ResponseState.Error).error}")
                    }
                }
                is ResponseState.Success -> {
                    //Success

                    //data
                    val listStudentData = (listStudentState as ResponseState.Success).data

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(listStudentData) { student ->
                            StudentCard(
                                imageStudent = painterResource(id = R.drawable.pp_deafult), //TODO CHANGE WITH REAL IMAGE
                                nameStudent = student.name,
                                numberStudent = student.nisn.toLong(),
                                onClick = { onDetailClick(student.email) }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }

            }

        } else if (role == Role.PARENT) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Kosong" )
            }
        }

    }
}