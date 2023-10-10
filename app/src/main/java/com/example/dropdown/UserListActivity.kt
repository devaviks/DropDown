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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.dropdown.ui.theme.DropDownTheme
import androidx.paging.PagingConfig
import androidx.paging.compose.LazyPagingItems

class UserListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownTheme {
                DisplayDataScreen()
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState", "RememberReturnType")
@Composable
fun DisplayDataScreen() {
    val context = LocalContext.current
    val dbHandler = DBHandler(context)
    val pagingConfig = PagingConfig(
        pageSize = 6,
        enablePlaceholders = false
    )

    val pager = remember {
        Pager(
            config = pagingConfig,
            pagingSourceFactory = { CoursePagingSource(dbHandler, PAGE_SIZE) }
        )
    }

    val lazyPagingItems: LazyPagingItems<Course> = pager.flow.collectAsLazyPagingItems()
    val loadState = lazyPagingItems.loadState

    val selectedCourses by remember { mutableStateOf(mutableListOf<Course>()) }

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
                .weight(1f),
        ) {
            items(lazyPagingItems.itemCount) { index ->
                val course = lazyPagingItems[index]
                var isSelected by remember { mutableStateOf(course?.isSelected ?: false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            isSelected = !isSelected
                            course?.let {
                                if (isSelected) {
                                    selectedCourses.add(it)
                                } else {
                                    selectedCourses.remove(it)
                                }
                            }
                        }
                        .background(
                            if (isSelected) Color.Yellow else Color.White
                        )
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color.Blue else Color.Transparent
                        ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = "Salutation: ${course?.salutation ?: ""}")
                        Text(text = "Name: ${course?.name ?: ""}")
                        Text(text = "Country: ${course?.country ?: ""}")
                        Text(text = "State: ${course?.state ?: ""}")
                        Text(text = "Gender: ${course?.gender ?: ""}")
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        )
                    }
                }
            }

            // Show a loading indicator at the end when data is being loaded
            if (loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            // Handle error state
            if (loadState.refresh is LoadState.Error) {
                item {
                    val errorState = loadState.refresh as LoadState.Error
                }
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, GroupCreateActivity::class.java)
                intent.putParcelableArrayListExtra(
                    "selectedCourses",
                    ArrayList(selectedCourses)
                )
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

 private const val PAGE_SIZE = 6
