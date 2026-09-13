package com.jewellery.shoporders.data

import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {
    val pendingOrders: Flow<List<Order>> = orderDao.getPendingOrders()
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    suspend fun insertOrder(order: Order): Long {
        return orderDao.insertOrder(order)
    }

    suspend fun updateOrder(order: Order) {
        orderDao.updateOrder(order)
    }

    suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrder(order)
    }

    suspend fun deleteOrderById(orderId: Long) {
        orderDao.deleteOrderById(orderId)
    }
}
