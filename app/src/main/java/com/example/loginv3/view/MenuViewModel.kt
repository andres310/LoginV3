package com.example.loginv3.view

import androidx.lifecycle.ViewModel
import com.example.loginv3.data.model.Product
import kotlinx.coroutines.flow.*

class MenuViewModel : ViewModel() {
    private var _menu = mutableListOf<Product>()
    private var _product = MutableStateFlow(Product())

    var product = _product.asStateFlow()

    fun add(product: Product) {
        _menu.add(product)
        println(_menu)
    }

    fun delete(name: String) {
        _menu = _menu.filter { it.name != name } as MutableList<Product>
    }
}