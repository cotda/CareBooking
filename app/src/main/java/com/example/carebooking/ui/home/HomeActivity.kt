package com.example.carebooking.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.ui.profile.ProfileActivity
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

                R.id.nav_home -> true

                R.id.nav_booking -> {
                    Toast.makeText(this, "Đặt lịch (giả lập)", Toast.LENGTH_SHORT).show()
                    false
                }

                R.id.nav_history -> {
                    Toast.makeText(this, "Lịch sử khám (giả lập)", Toast.LENGTH_SHORT).show()
                    false
                }

                R.id.nav_notifications -> {
                    Toast.makeText(this, "Thông báo (giả lập)", Toast.LENGTH_SHORT).show()
                    false
                }

                R.id.nav_account -> {
                    openProfileScreen()
                    false
                }

                else -> false
            }
        }
    }

    private fun openProfileScreen() {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra(ProfileActivity.EXTRA_FULL_NAME, "Nguyễn Văn An")
            putExtra(ProfileActivity.EXTRA_MEMBER_ID, "MED123456")
            putExtra(ProfileActivity.EXTRA_PHONE_NUMBER, "0123 456 789")
            putExtra(ProfileActivity.EXTRA_EMAIL, "nguyenvanan@email.com")
            putExtra(ProfileActivity.EXTRA_BIRTHDAY, "01/01/1990")
            putExtra(ProfileActivity.EXTRA_ADDRESS, "Hà Nội, Việt Nam")
        }
        startActivity(intent)
    }
}

