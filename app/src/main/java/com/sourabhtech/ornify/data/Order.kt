package com.sourabhtech.ornify.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a jewellery order in Ornify.
 */
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderType: String,
    val subCategory: String? = null,
    val approximatePrice: Double,
    val deliveryDateMillis: Long,
    val imagePaths: List<String> = emptyList(),
    val placedAtMillis: Long = System.currentTimeMillis(),
    val modifiedAtMillis: Long? = null,
    val isCompleted: Boolean = false
)
