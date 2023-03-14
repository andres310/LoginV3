package com.example.loginv3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.loginv3.view.MenuViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val menuViewModel : MenuViewModel by viewModels()

        val nombre = findViewById<TextInputLayout>(R.id.nombreTxt)
        nombre.editText?.doAfterTextChanged {
            menuViewModel.product.value.name = it.toString()
        }

        val precio = findViewById<TextInputLayout>(R.id.precioTxt)
        precio.editText?.doAfterTextChanged {
            if (it.toString() != "")
                menuViewModel.product.value.price = it.toString().toFloat()
        }

        val desc = findViewById<TextInputLayout>(R.id.descTxt)
        desc.editText?.doAfterTextChanged {
            menuViewModel.product.value.description = it.toString()
        }

        val posicion = findViewById<TextInputLayout>(R.id.posicionTxt)
        posicion.editText?.doAfterTextChanged {
            if (it.toString() != "")
                menuViewModel.product.value.menuPosition = it.toString().toInt()
        }

        val activo = findViewById<Switch>(R.id.activoSwitch)
        activo.setOnCheckedChangeListener { buttonView, isChecked ->
            menuViewModel.product.value.active = isChecked
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.product.collect {
                    // Updates ui
                    nombre.editText?.setText(it.name)
                    precio.editText?.setText(it.price.toString())
                    desc.editText?.setText(it.description)
                    posicion.editText?.setText(it.menuPosition.toString())
                    activo.isChecked = it.active
                }
            }
        }

        val btn = findViewById<Button>(R.id.saveBtn)

        // Añade un nuevo producto/servicio al menu
        btn.setOnClickListener {
            val prod = menuViewModel.product.value
            menuViewModel.add(prod)
            // Muestra mensaje
            Snackbar.make(it, "${prod.name} añadido en pos. ${prod.menuPosition}", Snackbar.LENGTH_LONG).show()
        }
    }
}