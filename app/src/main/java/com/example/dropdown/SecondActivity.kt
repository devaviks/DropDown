package com.example.dropdown

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dropdown.ui.theme.DropDownTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    DisplayUserData(intent)
                }
            }
        }
    }
}

@Composable
fun DisplayUserData(intent: Intent) {
    val name = intent.getStringExtra("Name")
    val gender = intent.getStringExtra("Gender")
    val country = intent.getStringExtra("country")
    val state = intent.getStringExtra("state")

    Text("Name: $name")
    Text("Gender: $gender")
    Text("Country: $country")
    Text("State: $state")
}

@Preview(showBackground = true)
@Composable
fun PreviewDisplayUserData() {
    DropDownTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            DisplayUserData(
                Intent().apply {
                    putExtra("Name", "John Doe")
                    putExtra("Gender", "Male")
                    putExtra("com.example.dropdown.Country", "India")
                    putExtra("com.example.dropdown.State", "West Bengal")
                }
            )
        }
    }
}
