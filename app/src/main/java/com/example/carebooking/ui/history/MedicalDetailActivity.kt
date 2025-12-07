package com.example.carebooking.ui.history

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.carebooking.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MedicalDetailActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_detail)

        setupToolbar()
        setupViewPager()
        setupButtons()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val adapter = MedicalDetailPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Đơn thuốc"
                1 -> "Kết quả xét nghiệm"
                else -> ""
            }
        }.attach()
    }

    private fun setupButtons() {
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnDownload).setOnClickListener {
            Toast.makeText(this, "Tải xuống", Toast.LENGTH_SHORT).show()
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnPrint).setOnClickListener {
            Toast.makeText(this, "In", Toast.LENGTH_SHORT).show()
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnShare).setOnClickListener {
            Toast.makeText(this, "Chia sẻ", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class MedicalDetailPagerAdapter(activity: AppCompatActivity) : 
        FragmentStateAdapter(activity) {
        
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PrescriptionFragment()
                1 -> TestResultsFragment()
                else -> PrescriptionFragment()
            }
        }
    }
}
