package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    var totalValue: String,
    var numberOfPeople: String,
    var valuePerPerson: String
): Parcelable
