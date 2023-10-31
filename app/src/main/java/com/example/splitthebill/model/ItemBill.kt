package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemBill(
    var name: String,
    var desc: String,
    var value: String
): Parcelable
