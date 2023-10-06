package com.example.dropdown

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdown.ui.theme.DropDownTheme


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownTheme {
                DisplayDataScreen()
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun DisplayDataScreen() {
    val context = LocalContext.current
    val dbHandler = DBHandler(context)

    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var selectedCourses: MutableList<Course> by remember { mutableStateOf(mutableListOf()) }

    LaunchedEffect(Unit) {
        val data = dbHandler.getAllCourses()
        courses = data
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Displaying Data",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(courses) { course ->
                var isSelected by remember { mutableStateOf(course.isSelected) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            isSelected = !isSelected
                            if (isSelected) {
                                selectedCourses.add(course)
                            } else {
                                selectedCourses.remove(course)
                            }
                        }
                        .background(
                            if (isSelected) Color.Yellow else Color.White
                        )
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color.Blue else Color.Transparent // Set the border color for selected item
                        ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Salutation: ${course.salutation}")
                        Text(text = "Name: ${course.name}")
                        Text(text = "Country: ${course.country}")
                        Text(text = "State: ${course.state}")
                        Text(text = "Gender: ${course.gender}")
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, ThirdActivity::class.java)
                intent.putParcelableArrayListExtra("selectedCourses", ArrayList(selectedCourses))
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Create Group")
        }
    }
}




