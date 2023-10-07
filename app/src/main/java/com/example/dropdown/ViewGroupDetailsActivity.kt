package com.example.dropdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dropdown.ui.theme.DropDownTheme

data class GroupUserDetails(
    val groupName: String,
    val salutation: String,
    val name: String,
    val country: String,
    val state: String,
    val gender: String
)

class ViewGroupDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewGroupDetails(dbHandler = DBHandler(this))
                }
            }
        }
    }
}

@Composable
fun ViewGroupDetails(dbHandler: DBHandler) {
    val groupUserDetails = dbHandler.getGroupUserDetails()

    LazyColumn {
        val groupedDetails = groupUserDetails.groupBy { it.groupName }
        groupedDetails.forEach { (groupName, details) ->
            item {
                GroupHeader(groupName = groupName)
            }
            item {
                UserDetailsList(details = details)
            }
            item {
                Divider() // Add a divider between groups
            }
        }
    }
}

@Composable
fun GroupHeader(groupName: String) {
    Text(
        text = "Group Name: $groupName",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun UserDetailsList(details: List<GroupUserDetails>) {
    LazyRow {
        items(details) { detail ->
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Salutation: ${detail.salutation}")
                    Text(text = "Name: ${detail.name}")
                    Text(text = "Country: ${detail.country}")
                    Text(text = "State: ${detail.state}")
                    Text(text = "Gender: ${detail.gender}")
                }
            }
        }
    }
}


