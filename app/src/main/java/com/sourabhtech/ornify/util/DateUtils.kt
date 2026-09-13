package com.sourabhtech.ornify.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val displayDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val placedDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    fun formatDate(millis: Long): String {
        return try {
            displayDateFormat.format(Date(millis))
        } catch (e: Exception) {
            ""
        }
    }

    fun formatPlacedDate(millis: Long): String {
        return try {
            placedDateFormat.format(Date(millis))
        } catch (e: Exception) {
            ""
        }
    }

    fun formatModifiedDate(millis: Long): String {
        return try {
            placedDateFormat.format(Date(millis))
        } catch (e: Exception) {
            ""
        }
    }

    fun formatCurrency(amount: Double): String {
        return try {
            val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            format.format(amount)
        } catch (e: Exception) {
            "₹ %,.2f".format(amount)
        }
    }
}
