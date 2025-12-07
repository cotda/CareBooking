package com.example.carebooking.ui.home.booking

data class Package(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val originalPrice: String,
    val popular: Boolean,
    val services: List<String>,
    val icon: String,
    val colorGradient: String // e.g., "blue", "red", "green", "pink"
) {
    fun getSavings(): String {
        val priceValue = price.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
        val originalValue = originalPrice.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
        val savings = originalValue - priceValue
        return "${savings / 1000}.${(savings % 1000) / 100}00.000đ"
    }
}
