package com.example.carebooking.ui.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.R
import com.example.carebooking.ui.history.HistoryActivity
import com.example.carebooking.ui.home.HomeActivity
import com.example.carebooking.ui.home.booking.BookingActivity
import com.example.carebooking.ui.notifications.NotificationActivity
import com.example.carebooking.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun AppCompatActivity.setupBottomNavigation(
    bottomNavigationView: BottomNavigationView,
    selectedItemId: Int
) {
    bottomNavigationView.selectedItemId = selectedItemId

    bottomNavigationView.setOnItemSelectedListener { item ->
        if (item.itemId == selectedItemId) {
            true
        } else {
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateTo(HomeActivity::class.java)
                    true
                }

                R.id.nav_booking -> {
                    navigateTo(BookingActivity::class.java)
                    true
                }

                R.id.nav_history -> {
                    navigateTo(HistoryActivity::class.java)
                    true
                }

                R.id.nav_notifications -> {
                    navigateTo(NotificationActivity::class.java)
                    true
                }

                R.id.nav_account -> {
                    navigateTo(ProfileActivity::class.java)
                    true
                }

                else -> false
            }
        }
    }

    bottomNavigationView.setOnItemReselectedListener { /* no-op */ }
}

private fun AppCompatActivity.navigateTo(destination: Class<out AppCompatActivity>) {
    val intent = Intent(this, destination).apply {
        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }
    startActivity(intent)
    overridePendingTransition(0, 0)
}


