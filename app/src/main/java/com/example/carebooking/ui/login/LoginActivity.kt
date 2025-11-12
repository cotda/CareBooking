package com.example.carebooking.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R // <-- Add this line
import com.example.carebooking.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var rememberMe: CheckBox
    private lateinit var forgotPassword: TextView
    private lateinit var loginButton: Button
    private lateinit var signUpLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        initializeViews()

        // Set click listeners
        setupListeners()
    }

    private fun initializeViews() {
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        rememberMe = findViewById(R.id.rememberMe)
        forgotPassword = findViewById(R.id.forgotPassword)
        loginButton = findViewById(R.id.loginButton)
        signUpLink = findViewById(R.id.signUpLink)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            handleLogin()
        }

        forgotPassword.setOnClickListener {
            // Navigate to forgot password screen
            Toast.makeText(this, "Quên mật khẩu - Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
        }

        signUpLink.setOnClickListener {
            // Navigate to sign up screen
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun handleLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        // Validation
        if (!validateInput(email, password)) {
            return
        }

        // TODO: Implement actual login logic with backend/Firebase
        // For now, just show success message
        Toast.makeText(this, "Đang đăng nhập...", Toast.LENGTH_SHORT).show()

        // Simulate successful login - navigate to main screen
        // startActivity(Intent(this, MainActivity::class.java))
        // finish()
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                emailInput.error = "Vui lòng nhập email"
                false
            }
            !isValidEmail(email) -> {
                emailInput.error = "Email không hợp lệ"
                false
            }
            password.isEmpty() -> {
                passwordInput.error = "Vui lòng nhập mật khẩu"
                false
            }
            password.length < 6 -> {
                passwordInput.error = "Mật khẩu phải có ít nhất 6 ký tự"
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}