/*package com.example.dropdown

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdown.ui.theme.DropDownTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownTheme {
                @Suppress("DEPRECATION")
                val selectedItems = intent?.getParcelableArrayListExtra<ImageWithTextInfo>(
                    "selectedItems"
                ) ?: emptyList()
                ThirdActivityContent(selectedItems)
                Log.d("ThirdActivity", "Selected Items: $selectedItems")
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdActivityContent(selectedItems: List<ImageWithTextInfo>) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Selected Users",
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (selectedItems.isEmpty()) {
                    Text(
                        text = "No users selected.",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else {
                    for (item in selectedItems) {
                        Text(
                            text = "${item.name ?: ""} - ${item.location ?: ""}",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    )
}*/
