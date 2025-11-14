package com.example.carebooking.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.data.network.ApiService
import com.example.carebooking.data.network.RetrofitClient
import com.example.carebooking.utils.ToastUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var inputToken: EditText
    private lateinit var inputNewPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        inputToken = findViewById(R.id.inputToken)
        inputNewPassword = findViewById(R.id.inputNewPassword)
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword)
        btnReset = findViewById(R.id.btnResetPassword)

        // LẤY TOKEN TỪ INTENT VÀ ĐIỀN VÀO
        val token = intent.getStringExtra("token")
        val email = intent.getStringExtra("email")

        inputToken.setText(token)

        btnReset.setOnClickListener {
            performReset()
        }
    }

    private fun performReset() {
        val token = inputToken.text.toString().trim()
        val newPass = inputNewPassword.text.toString().trim()
        val confirm = inputConfirmPassword.text.toString().trim()

        if (token.isEmpty()) {
            inputToken.error = "Vui lòng nhập token"
            inputToken.requestFocus()
            return
        }

        if (newPass.length < 6) {
            inputNewPassword.error = "Mật khẩu tối thiểu 6 ký tự"
            inputNewPassword.requestFocus()
            return
        }

        if (newPass != confirm) {
            inputConfirmPassword.error = "Mật khẩu không khớp"
            inputConfirmPassword.requestFocus()
            return
        }

        // Loading animation
        btnReset.isEnabled = false
        btnReset.alpha = 0.6f
        btnReset.text = "Đang xử lý..."

        val api = RetrofitClient.instance.create(ApiService::class.java)
        val data = mapOf(
            "token" to token,
            "newPassword" to newPass
        )

        api.resetPassword(data).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                btnReset.isEnabled = true
                btnReset.alpha = 1f
                btnReset.text = "Đặt lại mật khẩu"

                if (response.isSuccessful) {
                    ToastUtils.showSuccess(this@ResetPasswordActivity, "Đặt lại mật khẩu thành công!")
                    startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
                    finish()
                } else {
                    ToastUtils.showError(this@ResetPasswordActivity, "Token không hợp lệ hoặc hết hạn")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                btnReset.isEnabled = true
                btnReset.alpha = 1f
                btnReset.text = "Đặt lại mật khẩu"

                ToastUtils.showError(this@ResetPasswordActivity, "Lỗi kết nối: ${t.message}")
            }
        })
    }
}
