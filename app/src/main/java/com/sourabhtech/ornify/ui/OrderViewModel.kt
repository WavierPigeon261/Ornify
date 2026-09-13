package com.sourabhtech.ornify.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sourabhtech.ornify.data.Order
import com.sourabhtech.ornify.data.OrderDatabase
import com.sourabhtech.ornify.data.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OrderRepository

    val pendingOrders: StateFlow<List<Order>>
    val completedOrders: StateFlow<List<Order>>

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> = _selectedOrder.asStateFlow()

    private val _orderToEdit = MutableStateFlow<Order?>(null)
    val orderToEdit: StateFlow<Order?> = _orderToEdit.asStateFlow()

    init {
        val database = OrderDatabase.getDatabase(application)
        repository = OrderRepository(database.orderDao())

        pendingOrders = repository.pendingOrders.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        completedOrders = repository.completedOrders.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun selectOrder(order: Order?) {
        _selectedOrder.value = order
    }

    fun setOrderToEdit(order: Order?) {
        _orderToEdit.value = order
    }

    fun saveOrder(
        existingOrderId: Long? = null,
        orderType: String,
        subCategory: String?,
        approximatePrice: Double,
        deliveryDateMillis: Long,
        imagePaths: List<String>,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            if (existingOrderId != null && existingOrderId > 0) {
                // Updating existing order
                val existing = repository.getOrderById(existingOrderId)
                if (existing != null) {
                    val updated = existing.copy(
                        orderType = orderType,
                        subCategory = subCategory,
                        approximatePrice = approximatePrice,
                        deliveryDateMillis = deliveryDateMillis,
                        imagePaths = imagePaths,
                        modifiedAtMillis = System.currentTimeMillis()
                    )
                    repository.updateOrder(updated)
                    if (_selectedOrder.value?.id == existingOrderId) {
                        _selectedOrder.value = updated
                    }
                }
            } else {
                // Creating new order
                val newOrder = Order(
                    orderType = orderType,
                    subCategory = subCategory,
                    approximatePrice = approximatePrice,
                    deliveryDateMillis = deliveryDateMillis,
                    imagePaths = imagePaths,
                    placedAtMillis = System.currentTimeMillis(),
                    modifiedAtMillis = null,
                    isCompleted = false
                )
                repository.insertOrder(newOrder)
            }
            _orderToEdit.value = null
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

    fun reopenOrder(order: Order) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(isCompleted = false))
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

    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteCompletedOrders()
        }
    }
}
