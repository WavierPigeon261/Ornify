package com.sourabhtech.ornify.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE isCompleted = 0 ORDER BY placedAtMillis DESC")
    fun getPendingOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE isCompleted = 1 ORDER BY COALESCE(modifiedAtMillis, placedAtMillis) DESC")
    fun getCompletedOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders ORDER BY placedAtMillis DESC")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: Long): Order?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrderById(orderId: Long)

    @Query("DELETE FROM orders WHERE isCompleted = 1")
    suspend fun deleteCompletedOrders()
}
