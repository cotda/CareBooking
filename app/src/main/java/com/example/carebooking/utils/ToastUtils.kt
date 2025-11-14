package com.example.carebooking.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.carebooking.R

object ToastUtils {

    fun showError(context: Context, message: String) {
        showToast(context, message, isError = true)
    }

    fun showSuccess(context: Context, message: String) {
        showToast(context, message, isError = false)
    }

    private fun showToast(context: Context, message: String, isError: Boolean) {
        val inflater = LayoutInflater.from(context)

        // layout toast chung
        val layout = inflater.inflate(
            R.layout.custom_toast,
            null
        )

        // đổi background theo loại
        val bg = if (isError) R.drawable.bg_toast_error else R.drawable.bg_toast_success
        layout.setBackgroundResource(bg)

        val text = layout.findViewById<TextView>(R.id.toastText)
        text.text = message

        Toast(context).apply {
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}
