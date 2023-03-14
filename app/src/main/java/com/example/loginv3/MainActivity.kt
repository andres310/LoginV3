package com.example.loginv3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.loginv3.view.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginViewModel : LoginViewModel by viewModels()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.user.collect { value ->
                    val userInput = findViewById<TextView>(R.id.userTxt)
                    userInput.setText(value)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.password.collect { value ->
                    val passwordInput = findViewById<TextView>(R.id.passwordTxt)
                    passwordInput.setText(value)
                }
            }
        }

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val user = findViewById<TextView>(R.id.userTxt).text.toString()
            val password = findViewById<TextView>(R.id.passwordTxt).text.toString()
            val msg = loginViewModel.login(user, password)

            val nextActivity = Intent(this, ProductoActivity::class.java)

            if (msg != "Access Denied!") {
                // Pasa a la sig activity
                startActivity(nextActivity)
            } else {
                // Muestra mensaje
                Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show()
            }

        }
    }
}