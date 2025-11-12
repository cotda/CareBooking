package com.example.carebooking.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var fullNameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var termsCheckbox: CheckBox
    private lateinit var termsLink: TextView
    private lateinit var privacyLink: TextView
    private lateinit var signUpButton: Button
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize views
        initializeViews()

        // Set click listeners
        setupListeners()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        fullNameInput = findViewById(R.id.fullNameInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        termsLink = findViewById(R.id.termsLink)
        privacyLink = findViewById(R.id.privacyLink)
        signUpButton = findViewById(R.id.signUpButton)
        loginLink = findViewById(R.id.loginLink)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish()
        }

        signUpButton.setOnClickListener {
            handleSignUp()
        }

        termsLink.setOnClickListener {
            Toast.makeText(this, "Điều khoản và điều kiện - Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to terms and conditions page
        }

        privacyLink.setOnClickListener {
            Toast.makeText(this, "Chính sách bảo mật - Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to privacy policy page
        }

        loginLink.setOnClickListener {
            // Navigate back to login screen
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun handleSignUp() {
        val fullName = fullNameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()
        val termsAccepted = termsCheckbox.isChecked

        // Validation
        if (!validateInput(fullName, phone, email, password, confirmPassword, termsAccepted)) {
            return
        }

        // TODO: Implement actual sign up logic with backend/Firebase
        // For now, just show success message
        Toast.makeText(this, "Đang tạo tài khoản...", Toast.LENGTH_SHORT).show()

        // Simulate successful signup - navigate to login screen
        // startActivity(Intent(this, LoginActivity::class.java))
        // finish()
    }

    private fun validateInput(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean
    ): Boolean {
        return when {
            fullName.isEmpty() -> {
                fullNameInput.error = "Vui lòng nhập họ tên"
                false
            }
            fullName.length < 3 -> {
                fullNameInput.error = "Họ tên phải có ít nhất 3 ký tự"
                false
            }
            phone.isEmpty() -> {
                phoneInput.error = "Vui lòng nhập số điện thoại"
                false
            }
            !isValidPhone(phone) -> {
                phoneInput.error = "Số điện thoại không hợp lệ"
                false
            }
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
            confirmPassword.isEmpty() -> {
                confirmPasswordInput.error = "Vui lòng xác nhận mật khẩu"
                false
            }
            password != confirmPassword -> {
                confirmPasswordInput.error = "Mật khẩu không khớp"
                false
            }
            !termsAccepted -> {
                Toast.makeText(this, "Vui lòng chấp nhận Điều khoản và Chính sách bảo mật", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        // Vietnamese phone number validation (simple)
        return phone.length >= 9 && phone.all { it.isDigit() || it == '+' }
    }
}