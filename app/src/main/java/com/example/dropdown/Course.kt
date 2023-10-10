package com.example.dropdown

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
    val _id: Int?,
    val salutation: String,
    val name: String,
    val country: String,
    val state: String,
    val gender: String,
    val imageBase64: String, // Add the imageBase64 property
    var isSelected: Boolean = false
): Parcelable
