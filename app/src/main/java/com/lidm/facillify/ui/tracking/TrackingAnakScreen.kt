package com.lidm.facillify.ui.tracking

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidm.facillify.ui.ViewModelFactory
import com.lidm.facillify.ui.components.StudentCard
import com.lidm.facillify.ui.responseStateScreen.LoadingScreen
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
    val preferences by trackingViewModel.getSession().observeAsState()

    val listChildState by trackingViewModel.listChild.collectAsState()

    //launchedeffect
    LaunchedEffect(role == Role.TEACHER) {
        trackingViewModel.getAllStudent()
    }

    LaunchedEffect(role == Role.PARENT) {
        trackingViewModel.getAllChild()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(role == Role.TEACHER) {
            when (listStudentState) {
                is ResponseState.Loading -> {
                    //Loading
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingScreen()
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

                    if (listStudentData.isEmpty()) {
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Data Siswa Belum Ditemukan!")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //TODO GET PROFILE BY EMAIL STUDENT

                            items(listStudentData) { student ->
                                //state
                                //val profileState by trackingViewModel.getProfileUrl.collectAsState()
                                val profileState by trackingViewModel.getProfileUrlFlow(student.email).collectAsState()

                                LaunchedEffect(student.email) {
                                    trackingViewModel.getProfileUrl(student.email)
                                }
                                //trackingViewModel.getProfileUrl(student.email)
                                when (profileState) {
                                    is ResponseState.Success -> {
                                        val tempProfileUrl = (profileState as ResponseState.Success).data.result.profile_image_url

                                        Log.d("TrackingAnakScreen", "tempProfileUrl: $tempProfileUrl")

                                        StudentCard(
                                            linkImage = tempProfileUrl?.toString() ?: "",
                                            nameStudent = student.name,
                                            learnigStyle = student.learning_style.toString(),
                                            onClick = { onDetailClick(student.email) },
                                            bearerToken = preferences?.token.toString()
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    is ResponseState.Error -> {
                                        //Error
                                        StudentCard(
                                            linkImage = "", // Use a placeholder image or empty string
                                            nameStudent = student.name,
                                            learnigStyle = student.learning_style.toString(),
                                            onClick = { onDetailClick(student.email) },
                                            bearerToken = preferences?.token.toString()
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    is ResponseState.Loading -> {
                                        //Loading
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else if (role == Role.PARENT) {
            when (listChildState) {
                is ResponseState.Loading -> {
                    //Loading
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingScreen()
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
                    val listChildData = (listChildState as ResponseState.Success).data

                    if (listChildData.isEmpty()) {
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Data Anak Belum Ditemukan!")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //TODO GET PROFILE BY EMAIL STUDENT
                            items(listChildData) { student ->
                                //state
                                //val profileState by trackingViewModel.getProfileUrl.collectAsState()
                                val profileState by trackingViewModel.getProfileUrlFlow(student.email).collectAsState()

                                LaunchedEffect(student.email) {
                                    trackingViewModel.getProfileUrl(student.email)
                                }
                                //trackingViewModel.getProfileUrl(student.email)
                                when (profileState) {
                                    is ResponseState.Success -> {
                                        val tempProfileUrl = (profileState as ResponseState.Success).data.result.profile_image_url

                                        Log.d("TrackingAnakScreen", "tempProfileUrl: $tempProfileUrl")

                                        StudentCard(
                                            linkImage = tempProfileUrl?.toString() ?: "",
                                            nameStudent = student.name,
                                            learnigStyle = student.learning_style.toString(),
                                            onClick = { onDetailClick(student.email) },
                                            bearerToken = preferences?.token.toString()
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    is ResponseState.Error -> {
                                        //Error
                                        StudentCard(
                                            linkImage = "", // Use a placeholder image or empty string
                                            nameStudent = student.name,
                                            learnigStyle = student.learning_style.toString(),
                                            onClick = { onDetailClick(student.email) },
                                            bearerToken = preferences?.token.toString()
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    is ResponseState.Loading -> {
                                        //Loading
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }

    }
}