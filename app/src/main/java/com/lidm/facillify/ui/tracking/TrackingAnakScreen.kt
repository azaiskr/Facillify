package com.lidm.facillify.ui.tracking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lidm.facillify.R
import com.lidm.facillify.data.Student
import com.lidm.facillify.ui.components.MainTopAppBar
import com.lidm.facillify.ui.components.StudentCard
import com.lidm.facillify.util.Role

@Composable
fun TrackingAnakScreen(
    onDetailClick: (Student) -> Unit,
    studentList: List<Student>,
    userRole: Role
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MainTopAppBar(
            profileIcon = false,
            sectionTitle = if (userRole == Role.TEACHER) "Tracking Siswa" else if (userRole == Role.PARENT) "Tracking Anak" else "",
            onProfileClick = {},
            onBackClick = {},
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(studentList.size) { index ->
                StudentCard(
                    imageStudent = painterResource(id = studentList[index].image),
                    nameStudent = studentList[index].name,
                    numberStudent = studentList[index].number,
                    onClick = { onDetailClick(studentList[index]) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TrackingAnakScreenPreview() {
    TrackingAnakScreen(
        onDetailClick = {},
        userRole = Role.TEACHER,
        studentList = listOf(
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
            Student(
                image = R.drawable.pp_deafult,
                name = "Rizky",
                number = 1
            ),
        )
    )
}