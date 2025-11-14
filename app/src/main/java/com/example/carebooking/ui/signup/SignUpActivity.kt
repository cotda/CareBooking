package com.example.carebooking.ui.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.data.network.ApiService
import com.example.carebooking.data.network.RetrofitClient
import com.example.carebooking.ui.login.LoginActivity
import com.example.carebooking.utils.ToastUtils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var fullNameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var btnShowPassword: ImageView
    private lateinit var btnShowConfirmPassword: ImageView
    private lateinit var termsCheckbox: CheckBox
    private lateinit var textTerms: TextView
    private lateinit var signUpButton: Button
    private lateinit var loginLink: TextView

    private var showPassword = false
    private var showConfirmPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initializeViews()
        setupListeners()
        setupTermsText()
    }

    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        fullNameInput = findViewById(R.id.fullNameInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        btnShowPassword = findViewById(R.id.btnShowPassword)
        btnShowConfirmPassword = findViewById(R.id.btnShowConfirmPassword)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        textTerms = findViewById(R.id.textTerms)
        signUpButton = findViewById(R.id.signUpButton)
        loginLink = findViewById(R.id.loginLink)
    }

    private fun setupListeners() {
        backButton.setOnClickListener { finish() }

        btnShowPassword.setOnClickListener {
            showPassword = !showPassword
            togglePasswordVisibility(passwordInput, btnShowPassword, showPassword)
        }

        btnShowConfirmPassword.setOnClickListener {
            showConfirmPassword = !showConfirmPassword
            togglePasswordVisibility(confirmPasswordInput, btnShowConfirmPassword, showConfirmPassword)
        }

        signUpButton.setOnClickListener { handleSignUp() }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupTermsText() {
        val fullText = "Tôi đồng ý với Điều khoản dịch vụ và Chính sách bảo mật"
        val spannable = SpannableString(fullText)

        val term1 = "Điều khoản dịch vụ"
        val term2 = "Chính sách bảo mật"

        val t1 = fullText.indexOf(term1)
        val t2 = fullText.indexOf(term2)

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#2563EB")),
            t1, t1 + term1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#2563EB")),
            t2, t2 + term2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtils.showSuccess(this@SignUpActivity, "Điều khoản dịch vụ")
            }
        }, t1, t1 + term1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtils.showSuccess(this@SignUpActivity, "Chính sách bảo mật")
            }
        }, t2, t2 + term2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textTerms.text = spannable
        textTerms.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun togglePasswordVisibility(editText: EditText, icon: ImageView, show: Boolean) {
        if (show) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            icon.setImageResource(R.drawable.ic_eye_off)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            icon.setImageResource(R.drawable.ic_eye)
        }
        editText.setSelection(editText.text.length)
    }

    private fun handleSignUp() {

        val fullName = fullNameInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()

        // validate input - dùng toast utils
        if (!validateInput(fullName, phone, email, password, confirmPassword, termsCheckbox.isChecked))
            return

        val api = RetrofitClient.instance.create(ApiService::class.java)
        val registerData = mapOf(
            "fullName" to fullName,
            "phone" to phone,
            "email" to email,
            "password" to password
        )

        api.registerUser(registerData)
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    if (response.isSuccessful) {
                        ToastUtils.showSuccess(this@SignUpActivity, "Đăng ký thành công!")
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val json = JSONObject(errorBody ?: "")
                        val msg = json.optString("message", "Lỗi không xác định")
                        ToastUtils.showError(this@SignUpActivity, msg)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ToastUtils.showError(this@SignUpActivity, "Không thể kết nối: ${t.message}")
                }
            })
    }

    private fun validateInput(
        fullName: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean
    ): Boolean {

        // 1️⃣ validate fullname
        if (fullName.isEmpty()) {
            fullNameInput.error = "Vui lòng nhập họ tên"
            fullNameInput.requestFocus()
            return false
        }

        // 2️⃣ validate phone
        val phoneRegex = Regex("^(0|84)(\\d){9}\$")   // Ví dụ: 098..., 084..., 091..., 077...
        if (phone.isEmpty()) {
            phoneInput.error = "Vui lòng nhập số điện thoại"
            phoneInput.requestFocus()
            return false
        }
        if (!phoneRegex.matches(phone)) {
            phoneInput.error = "Số điện thoại không hợp lệ"
            phoneInput.requestFocus()
            return false
        }

        // 3️⃣ validate email
        if (email.isEmpty()) {
            emailInput.error = "Vui lòng nhập email"
            emailInput.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Email không hợp lệ"
            emailInput.requestFocus()
            return false
        }

        // 4️⃣ validate password
        if (password.isEmpty()) {
            passwordInput.error = "Vui lòng nhập mật khẩu"
            passwordInput.requestFocus()
            return false
        }
        if (password.length < 6) {
            passwordInput.error = "Mật khẩu ít nhất 6 ký tự"
            passwordInput.requestFocus()
            return false
        }

        // 5️⃣ validate confirm password
        if (confirmPassword != password) {
            confirmPasswordInput.error = "Mật khẩu không khớp"
            confirmPasswordInput.requestFocus()
            return false
        }

        // 6️⃣ validate checkbox
        if (!termsAccepted) {
            ToastUtils.showError(this, "Bạn phải chấp nhận điều khoản")
            return false
        }

        return true
    }
}