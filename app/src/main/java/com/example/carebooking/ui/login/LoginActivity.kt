package com.example.carebooking.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.data.network.ApiService
import com.example.carebooking.data.network.RetrofitClient
import com.example.carebooking.ui.signup.SignUpActivity
import com.example.carebooking.utils.ToastUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var rememberMe: CheckBox
    private lateinit var forgotPassword: TextView
    private lateinit var btnLogin: Button
    private lateinit var btnShowPassword: ImageView
    private lateinit var goToSignUp: TextView

    private var showPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        setupListeners()
        loadSavedLogin()
    }

    private fun initializeViews() {
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        rememberMe = findViewById(R.id.rememberMe)
        forgotPassword = findViewById(R.id.forgotPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnShowPassword = findViewById(R.id.btnShowPassword)
        goToSignUp = findViewById(R.id.goToSignUp)
    }

    private fun setupListeners() {
        btnShowPassword.setOnClickListener {
            showPassword = !showPassword
            togglePasswordVisibility(showPassword)
        }

        btnLogin.setOnClickListener {
            handleLogin()
        }

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        goToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun togglePasswordVisibility(show: Boolean) {
        if (show) {
            inputPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            btnShowPassword.setImageResource(R.drawable.ic_eye_off)
        } else {
            inputPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnShowPassword.setImageResource(R.drawable.ic_eye)
        }
        inputPassword.setSelection(inputPassword.text.length)
    }

    private fun handleLogin() {
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        if (!validateInput(email, password)) return

        val api = RetrofitClient.instance.create(ApiService::class.java)
        val loginData = mapOf("email" to email, "password" to password)

        api.loginUser(loginData).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                    ToastUtils.showSuccess(
                        this@LoginActivity,
                        "Đăng nhập thành công!"
                    )

                    // Remember Me save
                    if (rememberMe.isChecked) {
                        getSharedPreferences("CareBookingPrefs", MODE_PRIVATE).edit().apply {
                            putString("email", email)
                            putString("password", password)
                            putBoolean("rememberMe", true)
                            apply()
                        }
                    } else {
                        getSharedPreferences("CareBookingPrefs", MODE_PRIVATE)
                            .edit().clear().apply()
                    }

                } else {
                    ToastUtils.showError(
                        this@LoginActivity,
                        "Sai email hoặc mật khẩu"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                ToastUtils.showError(
                    this@LoginActivity,
                    "Lỗi kết nối: ${t.message}"
                )
            }
        })
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                inputEmail.error = "Vui lòng nhập email"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                inputEmail.error = "Email không hợp lệ"
                false
            }
            password.isEmpty() -> {
                inputPassword.error = "Vui lòng nhập mật khẩu"
                false
            }
            password.length < 6 -> {
                inputPassword.error = "Mật khẩu phải có ít nhất 6 ký tự"
                false
            }
            else -> true
        }
    }

    private fun loadSavedLogin() {
        val prefs = getSharedPreferences("CareBookingPrefs", MODE_PRIVATE)

        val savedEmail = prefs.getString("email", "")
        val savedPassword = prefs.getString("password", "")
        val isRemember = prefs.getBoolean("rememberMe", false)

        rememberMe.isChecked = isRemember
        inputEmail.setText(savedEmail)
        inputPassword.setText(savedPassword)
    }
}
