package com.example.carebooking.ui.home.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R

class PackagesFragment : Fragment() {
    
    private lateinit var rvPackages: RecyclerView
    private lateinit var packageAdapter: PackageAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_packages, container, false)
        
        rvPackages = view.findViewById(R.id.rvPackages)
        setupRecyclerView()
        
        return view
    }
    
    private fun setupRecyclerView() {
        val packages = listOf(
            Package(
                id = 1,
                name = "Gói khám sức khỏe tổng quát",
                description = "Khám toàn diện từ đầu đến chân",
                price = "1.500.000đ",
                originalPrice = "2.000.000đ",
                popular = true,
                services = listOf(
                    "Khám lâm sàng tổng quát",
                    "Xét nghiệm máu, nước tiểu",
                    "Siêu âm tổng quát",
                    "Đo điện tim",
                    "X-quang phổi",
                    "Tư vấn dinh dưỡng"
                ),
                icon = "🏥",
                colorGradient = "blue"
            ),
            Package(
                id = 2,
                name = "Gói khám tim mạch",
                description = "Chuyên sâu về tim mạch",
                price = "800.000đ",
                originalPrice = "1.000.000đ",
                popular = true,
                services = listOf(
                    "Khám tim mạch",
                    "Đo điện tim",
                    "Siêu âm tim",
                    "Xét nghiệm mỡ máu",
                    "Đo huyết áp 24h"
                ),
                icon = "❤️",
                colorGradient = "red"
            ),
            Package(
                id = 3,
                name = "Gói khám tiêu hóa",
                description = "Chăm sóc hệ tiêu hóa",
                price = "600.000đ",
                originalPrice = "800.000đ",
                popular = false,
                services = listOf(
                    "Khám tiêu hóa",
                    "Nội soi dạ dày",
                    "Siêu âm bụng",
                    "Xét nghiệm HP",
                    "Test thở Urea"
                ),
                icon = "🫁",
                colorGradient = "green"
            ),
            Package(
                id = 4,
                name = "Gói khám phụ nữ",
                description = "Chăm sóc sức khỏe phụ nữ",
                price = "900.000đ",
                originalPrice = "1.200.000đ",
                popular = true,
                services = listOf(
                    "Khám phụ khoa",
                    "Siêu âm vú",
                    "Siêu âm tử cung phần phụ",
                    "Xét nghiệm PAP",
                    "Khám tuyến vú"
                ),
                icon = "👩",
                colorGradient = "pink"
            )
        )
        
        packageAdapter = PackageAdapter(packages) { pkg ->
            // Handle booking click - show confirmation dialog
            showBookingConfirmation(pkg)
        }
        
        rvPackages.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = packageAdapter
        }
    }
    
    private fun showBookingConfirmation(pkg: Package) {
        // TODO: Show confirmation dialog
        android.widget.Toast.makeText(
            context,
            "Đặt ${pkg.name}",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}
