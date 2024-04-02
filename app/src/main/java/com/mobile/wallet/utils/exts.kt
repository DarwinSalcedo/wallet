package com.mobile.wallet.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.RequestPage
import androidx.compose.material.icons.filled.ShoppingCart
import java.text.NumberFormat
import java.util.Locale


fun Double.toCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return numberFormat.format(this)
}

fun String.toValidDouble(): Double {
    if (this == "") return 0.0
    if (this.length <= 2) return this.toDouble() / 100.0

    val integerPart = this.substring(0, this.length - 2)
    val decimalPart =
        this.substring(this.length - 2).toDouble() / 100.0
    return integerPart.toDouble() + decimalPart

}


val categories = listOf(
    "Home" to Icons.Default.Home,
    "Food" to Icons.Default.Fastfood,
    "Transport" to Icons.Default.DirectionsCar,
    "Shopping" to Icons.Default.ShoppingCart,
    "Health" to Icons.Default.LocalHospital,
    "Entertainment" to Icons.Default.Movie,
    "Fun" to Icons.Default.LocalDrink,
    "Utilities" to Icons.Default.Lightbulb,
    "Investment" to Icons.Default.Money,
    "Salary" to Icons.Default.Money,
    "Saving" to Icons.Default.Money,
    "Pet" to Icons.Default.Pets,
    "Beauty" to Icons.Default.AutoAwesome,
    "Loan" to Icons.Default.RequestPage,
    "Other" to Icons.Default.NotInterested,
)
val positiveCategories = listOf("Salary", "Saving", "Investment")
