package com.example.carebooking.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.ui.home.booking.BookingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Set tab mặc định
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    true
                }

                R.id.nav_booking -> {
                    val intent = Intent(this, BookingActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_history -> {
                    Toast.makeText(this, "Lịch sử khám (giả lập)", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_notifications -> {
                    Toast.makeText(this, "Thông báo (giả lập)", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_account -> {
                    Toast.makeText(this, "Tài khoản (giả lập)", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }
}
