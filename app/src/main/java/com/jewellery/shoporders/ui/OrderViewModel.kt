package com.jewellery.shoporders.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jewellery.shoporders.data.Order
import com.jewellery.shoporders.data.OrderDatabase
import com.jewellery.shoporders.data.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderRepository

    val pendingOrders: StateFlow<List<Order>>

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

    init {
        val database = OrderDatabase.getDatabase(application)
        repository = OrderRepository(database.orderDao())
        pendingOrders = repository.pendingOrders.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun selectOrder(order: Order?) {
        _selectedOrder.value = order
    }

    fun saveOrder(
        orderType: String,
        subCategory: String?,
        approximatePrice: Double,
        deliveryDateMillis: Long,
        imagePaths: List<String>,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val newOrder = Order(
                orderType = orderType,
                subCategory = subCategory,
                approximatePrice = approximatePrice,
                deliveryDateMillis = deliveryDateMillis,
                imagePaths = imagePaths,
                placedAtMillis = System.currentTimeMillis(),
                isCompleted = false
            )
            repository.insertOrder(newOrder)
            onSaved()
        }
    }

    fun markOrderCompleted(order: Order) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(isCompleted = true))
            if (_selectedOrder.value?.id == order.id) {
                _selectedOrder.value = null
            }
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            repository.deleteOrder(order)
            if (_selectedOrder.value?.id == order.id) {
                _selectedOrder.value = null
            }
        }
    }
}
