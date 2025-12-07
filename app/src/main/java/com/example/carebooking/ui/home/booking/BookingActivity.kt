package com.example.carebooking.ui.home.booking

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.carebooking.R
import com.example.carebooking.ui.common.setupBottomNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BookingActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var btnBack: ImageView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btnBack)
        bottomNav = findViewById(R.id.bottomNav)

        // Setup back button
        btnBack.setOnClickListener {
            finish()
        }

        // Setup bottom navigation
        setupBottomNavigation(bottomNav, R.id.nav_booking)

        val adapter = BookingPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Gói khám"
                1 -> tab.text = "Đặt lịch riêng"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_booking
    }
}
