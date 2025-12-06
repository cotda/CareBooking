package com.example.carebooking.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.ui.home.booking.BookingActivity
import com.example.carebooking.ui.profile.ProfileActivity
import com.example.carebooking.ui.common.setupBottomNavigation
import com.example.carebooking.ui.common.setupBottomNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottomNav)
        setupBottomNavigation(bottomNav, R.id.nav_home)
        bottomNav = findViewById(R.id.bottomNav)
        setupBottomNavigation(bottomNav, R.id.nav_home)
    }

    override fun onResume() {
        super.onResume()
    override fun onResume() {
        super.onResume()
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

