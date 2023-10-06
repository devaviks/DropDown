
package com.example.dropdown


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dropdown.ui.theme.DropDownTheme

class GroupCreateActivity : ComponentActivity() {

    lateinit  var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContent {
            @Suppress("DEPRECATION")
            val selectedCourses =
                intent.getParcelableArrayListExtra<Course>("selectedCourses") ?: emptyList()

            DropDownTheme {
                DisplayUserAndGroupData(selectedCourses)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DisplayUserAndGroupData(selectedCourses: List<Course>) {
        var groupName by remember { mutableStateOf("") }
        var savedData by remember { mutableStateOf<List<String>>(emptyList()) }
        val dbHandler: DBHandler = DBHandler(context)
        // Load the group name and saved data from SharedPreferences when the screen is created
        //val sharedPreferences = getSharedPreferences("GroupData", Context.MODE_PRIVATE)
       // groupName = sharedPreferences.getString("groupName", "") ?: ""

        //savedData = getSavedData(groupName, selectedCourses)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Selected Courses",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input field to enter the group name
            var textValue by remember { mutableStateOf("") }

            OutlinedTextField(
                value = textValue,
                onValueChange = { newText ->
                    textValue = newText
                },
                label = { Text("Enter Group Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Button to save the group name to SharedPreferences
            Button(
                onClick = {
                    val groupNameText = textValue.trim()
                    if (groupNameText.isNotEmpty()) {
                        // Save the group name to SharedPreferences
                        //saveGroupNameToSharedPreferences(groupNameText)

                        // Update the saved data list
                        //savedData = getSavedData(groupNameText, selectedCourses)
                        groupName = groupNameText // Set the group name

                        if(groupName.isNotEmpty()){
                            val groupId = dbHandler.addGroup(groupName)
                            var index = 0
                            while (index < selectedCourses.size) {
                                val userId = selectedCourses[index]._id

                                if (userId != null) {
                                    dbHandler.addGroupUser(userId.toLong(),groupId)
                                }
                                // Perform actions on obj
                                Log.e("USERIDS=========================", userId.toString())

                                index++
                            }

                        }



                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Save Group Name")
            }

            // Use LazyRow to display selected courses horizontally
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(selectedCourses) { course ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.Yellow)
                            .clickable { /* Handle item click if needed */ }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Salutation: ${course.salutation}")
                            Text(text = "Name: ${course.name}")
                            Text(text = "Country: ${course.country}")
                            Text(text = "State: ${course.state}")
                            Text(text = "Gender: ${course.gender}")
                        }
                    }
                }
            }

            // Display the saved data (group name and course names)
            if (savedData.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Group Name and Members Details",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                savedData.forEach { savedItem ->
                    Text(
                        text = savedItem,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }

    /*private fun saveGroupNameToSharedPreferences(groupName: String) {
        val sharedPreferences = getSharedPreferences("GroupData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("groupName", groupName)
        editor.apply()
    }*/

    private fun getSavedData(groupName: String, selectedCourses: List<Course>): List<String> {
        val savedDataList = mutableListOf<String>()

        // Add the group name to the saved data list
        if (groupName.isNotEmpty()) {
            savedDataList.add("Group: $groupName")

            // Add course names to the saved data list as a comma-separated string
            val groupMembers = selectedCourses.joinToString(", ") { course -> course.name }
            savedDataList.add("Group Members: $groupMembers")

        }

        return savedDataList
    }
}







