package com.sourabhtech.ornify.data

import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {
    val pendingOrders: Flow<List<Order>> = orderDao.getPendingOrders()
    val completedOrders: Flow<List<Order>> = orderDao.getCompletedOrders()
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    suspend fun getOrderById(orderId: Long): Order? {
        return orderDao.getOrderById(orderId)
    }

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

    suspend fun deleteCompletedOrders() {
        orderDao.deleteCompletedOrders()
    }
}
