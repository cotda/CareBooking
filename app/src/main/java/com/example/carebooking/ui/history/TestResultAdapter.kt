package com.example.carebooking.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R

class TestResultAdapter(private val testResults: List<TestResult>) : 
    RecyclerView.Adapter<TestResultAdapter.TestResultViewHolder>() {

    inner class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTestName: TextView = itemView.findViewById(R.id.tvTestName)
        val tvTestDate: TextView = itemView.findViewById(R.id.tvTestDate)
        val tvTestStatus: TextView = itemView.findViewById(R.id.tvTestStatus)
        val testItemsContainer: LinearLayout = itemView.findViewById(R.id.testItemsContainer)
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
        
        // Add test items dynamically
        testResult.items.forEach { item ->
            val itemView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.item_test_item, holder.testItemsContainer, false)
            
            // Set background based on status
            val backgroundRes = when (item.status) {
                TestStatus.HIGH -> R.drawable.bg_test_result_high
                TestStatus.LOW -> R.drawable.bg_test_result_low
                TestStatus.NORMAL -> R.drawable.bg_test_result_normal
            }
            itemView.findViewById<View>(R.id.testItemRoot).setBackgroundResource(backgroundRes)
            
            // Set test item data
            itemView.findViewById<TextView>(R.id.tvTestItemName).text = item.test
            itemView.findViewById<TextView>(R.id.tvNormalRange).text = "Bình thường: ${item.range}"
            itemView.findViewById<TextView>(R.id.tvTestValue).text = "${item.value} ${item.unit}"
            
            // Set abnormal indicator
            val tvAbnormal = itemView.findViewById<TextView>(R.id.tvAbnormalIndicator)
            when (item.status) {
                TestStatus.HIGH -> {
                    tvAbnormal.visibility = View.VISIBLE
                    tvAbnormal.text = "↑ Cao"
                    tvAbnormal.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.orange_600))
                }
                TestStatus.LOW -> {
                    tvAbnormal.visibility = View.VISIBLE
                    tvAbnormal.text = "↓ Thấp"
                    tvAbnormal.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.blue_600))
                }
                TestStatus.NORMAL -> {
                    tvAbnormal.visibility = View.GONE
                }
            }
            
            holder.testItemsContainer.addView(itemView)
        }
    }

    override fun getItemCount() = testResults.size
}
