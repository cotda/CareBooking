package com.example.carebooking.ui.history

data class TestResult(
    val id: Int,
    val name: String,
    val date: String,
    val status: String,
    val items: List<TestItem>
)

data class TestItem(
    val test: String,
    val value: String,
    val unit: String,
    val range: String,
    val status: TestStatus
)

enum class TestStatus {
    NORMAL, HIGH, LOW
}
