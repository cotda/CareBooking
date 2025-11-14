package com.example.carebooking.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.data.network.ApiService
import com.example.carebooking.data.network.RetrofitClient
import com.example.carebooking.utils.ToastUtils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnBack: ImageButton
    private lateinit var btnBackToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        inputEmail = findViewById(R.id.inputEmail)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnBack = findViewById(R.id.btnBack)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)

        // Nút Back icon
        btnBack.setOnClickListener {
            finish()
        }

        // Nút quay lại Login
        btnBackToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Nút gửi email reset
        btnSubmit.setOnClickListener {
            handleForgotPassword()
        }
    }

    private fun handleForgotPassword() {
        val email = inputEmail.text.toString().trim()

        if (email.isEmpty()) {
            inputEmail.error = "Vui lòng nhập email"
            inputEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Email không hợp lệ"
            inputEmail.requestFocus()
            return
        }

        // Hiệu ứng loading nút
        btnSubmit.isEnabled = false
        btnSubmit.alpha = 0.6f
        btnSubmit.text = "Đang gửi..."

        val api = RetrofitClient.instance.create(ApiService::class.java)
        val data = mapOf("email" to email)

        api.forgotPassword(data).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                // Reset nút về bình thường
                btnSubmit.isEnabled = true
                btnSubmit.alpha = 1f
                btnSubmit.text = getString(R.string.send_instructions)

                if (response.isSuccessful) {
                    val json = response.body()?.string()
                    val token = JSONObject(json).getString("resetToken")
                    val emailResponse = JSONObject(json).getString("email")

                    val intent = Intent(
                        this@ForgotPasswordActivity,
                        ResetPasswordActivity::class.java
                    )
                    intent.putExtra("token", token)
                    intent.putExtra("email", emailResponse)

                    startActivity(intent)
                    finish()
                } else {
                    ToastUtils.showError(
                        this@ForgotPasswordActivity,
                        "Email không tồn tại"
                    )
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Trả nút về trạng thái ban đầu
                btnSubmit.isEnabled = true
                btnSubmit.alpha = 1f
                btnSubmit.text = getString(R.string.send_instructions)

                ToastUtils.showError(
                    this@ForgotPasswordActivity,
                    "Lỗi kết nối: ${t.message}"
                )
            }
        })
    }
}
