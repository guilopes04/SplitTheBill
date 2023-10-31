package com.example.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    var name: String,
    var valorPago: String,
    var compra1: ItemBill,
    var compra2: ItemBill,
    var compra3: ItemBill,
    var valorAPagarAutomatico: String,
    var valorAPagarFixo: String
): Parcelable
