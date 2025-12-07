package com.example.carebooking.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R

class TestResultsActivity : AppCompatActivity() {

    private lateinit var rvTestResults: RecyclerView
    private lateinit var btnBack: ImageView
    private lateinit var btnDownload: View
    private lateinit var btnShare: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_results)

        // Initialize views
        rvTestResults = findViewById(R.id.rvTestResults)
        btnBack = findViewById(R.id.btnBack)
        btnDownload = findViewById(R.id.btnDownload)
        btnShare = findViewById(R.id.btnShare)

        // Setup RecyclerView
        rvTestResults.layoutManager = LinearLayoutManager(this)
        rvTestResults.adapter = TestResultsAdapter(getSampleData())

        // Setup click listeners
        btnBack.setOnClickListener { finish() }
        btnDownload.setOnClickListener {
            Toast.makeText(this, "Đang tải xuống...", Toast.LENGTH_SHORT).show()
        }
        btnShare.setOnClickListener {
            Toast.makeText(this, "Chia sẻ kết quả", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSampleData(): List<TestResult> {
        return listOf(
            TestResult(
                id = 1,
                name = "Xét nghiệm máu tổng quát",
                date = "10/11/2024",
                status = "Đã có kết quả",
                items = listOf(
                    TestItem("Hồng cầu", "4.8", "T/L", "4.0-5.5", TestStatus.NORMAL),
                    TestItem("Bạch cầu", "7.2", "G/L", "4.0-10.0", TestStatus.NORMAL),
                    TestItem("Tiểu cầu", "245", "G/L", "150-400", TestStatus.NORMAL),
                    TestItem("Hemoglobin", "14.5", "g/dL", "13.0-17.0", TestStatus.NORMAL)
                )
            ),
            TestResult(
                id = 2,
                name = "Xét nghiệm sinh hóa",
                date = "10/11/2024",
                status = "Đã có kết quả",
                items = listOf(
                    TestItem("Glucose", "105", "mg/dL", "70-100", TestStatus.HIGH),
                    TestItem("Cholesterol", "220", "mg/dL", "<200", TestStatus.HIGH),
                    TestItem("HDL-C", "45", "mg/dL", ">40", TestStatus.NORMAL),
                    TestItem("LDL-C", "145", "mg/dL", "<130", TestStatus.HIGH),
                    TestItem("Triglyceride", "175", "mg/dL", "<150", TestStatus.HIGH)
                )
            )
        )
    }

    // Adapter for test results
    private inner class TestResultsAdapter(private val testResults: List<TestResult>) :
        RecyclerView.Adapter<TestResultsAdapter.TestResultViewHolder>() {

        inner class TestResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTestName: TextView = view.findViewById(R.id.tvTestName)
            val tvTestDate: TextView = view.findViewById(R.id.tvTestDate)
            val tvTestStatus: TextView = view.findViewById(R.id.tvTestStatus)
            val testItemsContainer: LinearLayout = view.findViewById(R.id.testItemsContainer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_test_group, parent, false)
            return TestResultViewHolder(view)
        }

        override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
            val testResult = testResults[position]
            holder.tvTestName.text = testResult.name
            holder.tvTestDate.text = testResult.date
            holder.tvTestStatus.text = testResult.status

            // Clear previous items
            holder.testItemsContainer.removeAllViews()

            // Add test items
            testResult.items.forEach { item ->
                val itemView = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.item_test_result, holder.testItemsContainer, false)

                val tvTestItemName = itemView.findViewById<TextView>(R.id.tvTestItemName)
                val tvTestItemRange = itemView.findViewById<TextView>(R.id.tvTestItemRange)
                val tvTestItemValue = itemView.findViewById<TextView>(R.id.tvTestItemValue)
                val tvTestItemUnit = itemView.findViewById<TextView>(R.id.tvTestItemUnit)
                val statusIndicator = itemView.findViewById<LinearLayout>(R.id.statusIndicator)
                val ivStatusIcon = itemView.findViewById<ImageView>(R.id.ivStatusIcon)
                val tvStatusText = itemView.findViewById<TextView>(R.id.tvStatusText)

                tvTestItemName.text = item.test
                tvTestItemRange.text = "Bình thường: ${item.range}"
                tvTestItemValue.text = item.value
                tvTestItemUnit.text = item.unit

                // Set background and status based on test status
                when (item.status) {
                    TestStatus.HIGH -> {
                        itemView.setBackgroundResource(R.drawable.bg_test_result_high)
                        statusIndicator.visibility = View.VISIBLE
                        ivStatusIcon.setImageResource(R.drawable.ic_arrow_up_red)
                        tvStatusText.text = "Cao"
                        tvStatusText.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                        tvTestItemValue.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                    }
                    TestStatus.LOW -> {
                        itemView.setBackgroundResource(R.drawable.bg_test_result_low)
                        statusIndicator.visibility = View.VISIBLE
                        ivStatusIcon.setImageResource(R.drawable.ic_arrow_down_blue)
                        tvStatusText.text = "Thấp"
                        tvStatusText.setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                        tvTestItemValue.setTextColor(resources.getColor(android.R.color.holo_blue_dark, null))
                    }
                    TestStatus.NORMAL -> {
                        itemView.setBackgroundResource(R.drawable.bg_test_result_normal)
                        statusIndicator.visibility = View.GONE
                    }
                }

                holder.testItemsContainer.addView(itemView)
            }
        }

        override fun getItemCount() = testResults.size
    }
}
