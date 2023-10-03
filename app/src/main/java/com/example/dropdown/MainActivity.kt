package com.example.dropdown

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dropdown.ui.theme.DropDownTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownTheme {
                Column (
                    modifier = Modifier.fillMaxSize().padding(30.dp),
                ) {
                    FormContent()
                    OutLineTextFieldSample()
                    Country()
                    State()
                    RadioButtons()
                    MyButton()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormContent() {
    val context = LocalContext.current
    val gender = arrayOf("Mr.", "Mrs.")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(gender[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            gender.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineTextFieldSample() {
    var text by remember { mutableStateOf(TextFieldValue()) }
    OutlinedTextField(
        value = text,
        label = { Text(text = "Enter Your Name") },
        onValueChange = {
            text = it
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country() {
    val context = LocalContext.current
    val country = arrayOf("India", "Nepal", "Sri Lanka")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(country[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            country.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun State() {
    val context = LocalContext.current
    val country = arrayOf("West Bengal", "Bangalore", "Delhi")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(country[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            country.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RadioButtons() {
    val radioOptions = listOf("Male", "Female")

    var selectedItem by remember {
        mutableStateOf(radioOptions[0])
    }

    Column(modifier = Modifier.selectableGroup()) {
        radioOptions.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .selectable(
                        selected = (selectedItem == label),
                        onClick = { selectedItem = label },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(end = 16.dp),
                    selected = (selectedItem == label),
                    onClick = null
                )
                Text(text = label)
            }
        }
    }
}

@Composable
fun MyButton() {
    val context = LocalContext.current
    val radioOptions = listOf("Male", "Female")
    val selectedItem by remember { mutableStateOf(radioOptions[0]) }
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
    val selectedCountry = remember { mutableStateOf("India") }
    val selectedState = remember { mutableStateOf("West Bengal") }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                /*Log.d("MainActivity1============", "Selected Items: $textFieldValue.value.text")
                Log.d("MainActivity2============", "Selected Items: $selectedCountry.value")
                Log.d("MainActivity3============", "Selected Items: $selectedState.value")
                Log.d("MainActivity4============", "Selected Items: $selectedItem")*/


                val intent = Intent(context, SecondActivity::class.java).apply {
                    putExtra("Name", textFieldValue.value.text)
                    putExtra("Gender", selectedItem)
                    putExtra("country", selectedCountry.value)
                    putExtra("state", selectedState.value)
                }
                context.startActivity(intent)
            },
            modifier = Modifier.padding(all = Dp(10F)),
            enabled = true,
            border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(text = "Submit", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFormContent() {
    DropDownTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ){
            FormContent()
            OutLineTextFieldSample()
            Country()
            State()
            RadioButtons()
            MyButton()
        }
    }
}
