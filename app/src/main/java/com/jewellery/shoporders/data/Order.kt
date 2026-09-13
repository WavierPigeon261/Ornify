package com.jewellery.shoporders.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a customer's jewellery order.
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
    val isCompleted: Boolean = false
)
