package com.example.carebooking.ui.home.booking

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R
import com.google.android.material.button.MaterialButton

class PackageAdapter(
    private val packages: List<Package>,
    private val onBookClick: (Package) -> Unit
) : RecyclerView.Adapter<PackageAdapter.PackageViewHolder>() {

    inner class PackageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headerBackground: View = view.findViewById(R.id.headerBackground)
        val tvPackageIcon: TextView = view.findViewById(R.id.tvPackageIcon)
        val tvPackageName: TextView = view.findViewById(R.id.tvPackageName)
        val tvPackageDescription: TextView = view.findViewById(R.id.tvPackageDescription)
        val tvPopularBadge: TextView = view.findViewById(R.id.tvPopularBadge)
        val servicesContainer: LinearLayout = view.findViewById(R.id.servicesContainer)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvOriginalPrice: TextView = view.findViewById(R.id.tvOriginalPrice)
        val tvSavings: TextView = view.findViewById(R.id.tvSavings)
        val btnBookNow: MaterialButton = view.findViewById(R.id.btnBookNow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking_package, parent, false)
        return PackageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        val pkg = packages[position]
        val context = holder.itemView.context

        // Set gradient background
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            getGradientColors(pkg.colorGradient, context)
        )
        holder.headerBackground.background = gradient

        // Set basic info
        holder.tvPackageIcon.text = pkg.icon
        holder.tvPackageName.text = pkg.name
        holder.tvPackageDescription.text = pkg.description
        
        // Show/hide popular badge
        holder.tvPopularBadge.visibility = if (pkg.popular) View.VISIBLE else View.GONE

        // Add services
        holder.servicesContainer.removeAllViews()
        pkg.services.forEach { service ->
            val serviceView = LayoutInflater.from(context)
                .inflate(R.layout.item_service_check, holder.servicesContainer, false)
            serviceView.findViewById<TextView>(R.id.tvServiceName).text = service
            holder.servicesContainer.addView(serviceView)
        }

        // Set prices
        holder.tvPrice.text = pkg.price
        holder.tvOriginalPrice.text = pkg.originalPrice
        holder.tvOriginalPrice.paintFlags = holder.tvOriginalPrice.paintFlags or 
            android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        
        val savings = pkg.originalPrice.replace(Regex("[^0-9]"), "").toIntOrNull()?.minus(
            pkg.price.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
        ) ?: 0
        holder.tvSavings.text = "Tiết kiệm ${String.format("%,d", savings)}đ"

        // Set click listener
        holder.btnBookNow.setOnClickListener {
            onBookClick(pkg)
        }
    }

    override fun getItemCount() = packages.size

    private fun getGradientColors(colorName: String, context: android.content.Context): IntArray {
        return when (colorName) {
            "blue" -> intArrayOf(
                ContextCompat.getColor(context, R.color.blue_500),
                ContextCompat.getColor(context, R.color.blue_600)
            )
            "red" -> intArrayOf(
                ContextCompat.getColor(context, R.color.red_500),
                ContextCompat.getColor(context, R.color.red_600)
            )
            "green" -> intArrayOf(
                ContextCompat.getColor(context, R.color.green_500),
                ContextCompat.getColor(context, R.color.green_600)
            )
            "pink" -> intArrayOf(
                ContextCompat.getColor(context, R.color.pink_500),
                ContextCompat.getColor(context, R.color.pink_600)
            )
            else -> intArrayOf(
                ContextCompat.getColor(context, R.color.blue_500),
                ContextCompat.getColor(context, R.color.blue_600)
            )
        }
    }
}
