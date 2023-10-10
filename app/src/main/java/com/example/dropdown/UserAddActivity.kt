package com.example.dropdown

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.dropdown.ui.theme.DropDownTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dropdown.ui.theme.greenColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import android.util.Base64
import java.io.FileInputStream
import java.io.File



class UserAddActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
            DropDownTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold {
                        TopAppBar(
                            title = { Text(text = "") },
                            navigationIcon = {
                                TextButton(
                                    onClick = {
                                        val intent = Intent(
                                            this@UserAddActivity,
                                            ViewGroupDetailsActivity::class.java
                                        )
                                        startActivity(intent)
                                    }
                                ) {
                                    Text("View Group Details")
                                }
                            },
                            actions = {
                                TextButton(
                                    onClick = {
                                        val intent = Intent(
                                            this@UserAddActivity,
                                            UserListActivity::class.java
                                        )
                                        startActivity(intent)
                                    }
                                ) {
                                    Text("View User Details")
                                }
                            },
                        )
                    }
                    addDataToDatabase(LocalContext.current, selectedImageUri) { uri ->
                        selectedImageUri = uri as Uri?
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SalutationDropDown(
        selectedSalutation: String,
        onSalutationSelected: (String) -> Unit
    ) {
        val context = LocalContext.current
        val gender = arrayOf("Mr.", "Mrs.")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedSalutation,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                gender.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onSalutationSelected(item) // Update the selected salutation
                            expanded = false
                        }
                    )
                }
            }
        }
    }


    @Composable
    fun ImageFromGallery(onImageSelected: (Uri?) -> Unit) {
        val imageUri by remember { mutableStateOf<Uri?>(null) }
        val context = LocalContext.current
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                onImageSelected(uri)
            }

        Column {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Pick Image")
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropDownCountry(
        selectedCountry: String,
        onCountrySelected: (String) -> Unit
    ) {
        val context = LocalContext.current
        val country = arrayOf("India", "Nepal", "Sri Lanka")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedCountry,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                country.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onCountrySelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropDownState(
        selectedState: String,
        onStateSelected: (String) -> Unit
    ) {
        val context = LocalContext.current
        val country = arrayOf("West Bengal", "Bangalore", "Delhi")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedState,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                country.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onStateSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }


    @Composable
    fun RadioButtonsForGender(
        selectedGender: String,
        onGenderSelected: (String) -> Unit
    ) {
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
                            onClick = {
                                selectedItem = label
                                onGenderSelected(label)
                            },
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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun addDataToDatabase(
        context: Context,
        onImageSelected: Uri?, // Callback to send BASE64 image to the database
        param: (Any) -> Unit
    ) {
        val activity = context as Activity
        val courseName = remember { mutableStateOf(TextFieldValue()) }
        val courseDuration = remember { mutableStateOf(TextFieldValue()) }
        val selectedSalutation = remember { mutableStateOf("Mr.") }
        val selectedCountry = remember { mutableStateOf("India") }
        val selectedState = remember { mutableStateOf("West Bengal") }
        val selectedGender = remember { mutableStateOf("Male") }
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val dbHandler: DBHandler = DBHandler(context)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Please Fill the Form",
                color = greenColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            ImageFromGallery(onImageSelected = { uri ->
                imageUri = uri
            })

            Spacer(modifier = Modifier.height(20.dp))

            SalutationDropDown(
                selectedSalutation = selectedSalutation.value,
                onSalutationSelected = { selectedSalutation.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = courseDuration.value,
                onValueChange = { courseDuration.value = it },
                placeholder = { Text(text = "Enter your Name Please") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(20.dp))

            DropDownCountry(
                selectedCountry = selectedCountry.value,
                onCountrySelected = { selectedCountry.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            DropDownState(
                selectedState = selectedState.value,
                onStateSelected = { selectedState.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))
            RadioButtonsForGender(
                selectedGender = selectedGender.value,
                onGenderSelected = { selectedGender.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (imageUri != null) {
                    val imageBase64 = encodeImageToBase64(imageUri)
                    val course = Course(
                        _id = -1,
                        salutation = selectedSalutation.value,
                        name = courseDuration.value.text,
                        country = selectedCountry.value,
                        state = selectedState.value,
                        gender = selectedGender.value,
                        imageBase64 = imageBase64
                    )
                    dbHandler.addNewCourse(course)

                    val intent = Intent(context, UserListActivity::class.java)
                    context.startActivity(intent)

                    Toast.makeText(context, "Details Added to Database", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Add Details to Database", color = Color.White)
            }
        }
    }

    private fun encodeImageToBase64(imageUri: Uri?): String {
        if (imageUri == null) return ""

        try {
            val inputStream = if (imageUri.scheme == "content") {
                val contentResolver = applicationContext.contentResolver
                contentResolver.openInputStream(imageUri)
            } else {
                FileInputStream(imageUri.path?.let { File(it) })
            }

            val buffer = ByteArray(8192)
            var bytesRead: Int
            val output = ByteArrayOutputStream()

            inputStream.use { input ->
                if (input != null) {
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                    }
                }
            }

            return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }



}