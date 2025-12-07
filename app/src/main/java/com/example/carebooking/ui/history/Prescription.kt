package com.example.carebooking.ui.history

data class Prescription(
    val id: Int,
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String,
    val quantity: String,
    val usage: String,
    val notes: String?
)
