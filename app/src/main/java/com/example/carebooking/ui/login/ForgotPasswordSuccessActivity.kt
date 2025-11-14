package com.example.carebooking.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.data.network.ApiService
import com.example.carebooking.data.network.RetrofitClient
import com.example.carebooking.utils.ToastUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordSuccessActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnBackToLogin: Button
    private lateinit var btnResend: Button

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_success)

        tvEmail = findViewById(R.id.tvEmail)
        btnBack = findViewById(R.id.btnBackFromSuccess)
        btnBackToLogin = findViewById(R.id.btnBackToLoginSuccess)
        btnResend = findViewById(R.id.btnResendEmail)

        // Lấy email từ Intent
        email = intent.getStringExtra("email") ?: ""
        tvEmail.text = email

        btnBack.setOnClickListener { finish() }

        btnBackToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnResend.setOnClickListener {
            resendEmail()
        }
    }

    private fun resendEmail() {
        val api = RetrofitClient.instance.create(ApiService::class.java)

        api.forgotPassword(mapOf("email" to email))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        ToastUtils.showSuccess(
                            this@ForgotPasswordSuccessActivity,
                            "Đã gửi lại email!"
                        )
                    } else {
                        ToastUtils.showError(
                            this@ForgotPasswordSuccessActivity,
                            "Không thể gửi lại email"
                        )
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ToastUtils.showError(
                        this@ForgotPasswordSuccessActivity,
                        "Lỗi kết nối: ${t.message}"
                    )
                }
            })
    }
}
