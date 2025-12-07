package com.example.carebooking.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R

class TestResultsFragment : Fragment() {

    private lateinit var rvTestResults: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView(view)
        loadSampleData()
    }

    private fun setupRecyclerView(view: View) {
        rvTestResults = view.findViewById(R.id.rvTestResults)
        rvTestResults.layoutManager = LinearLayoutManager(requireContext())
        rvTestResults.isNestedScrollingEnabled = false
    }

    private fun loadSampleData() {
        val testResults = listOf(
            TestResult(
                id = 1,
                name = "Xét nghiệm máu tổng quát",
                date = "10/11/2024",
                status = "Đã có kết quả",
                items = listOf(
                    TestItem("Hồng cầu", "4.8", "T/L", "4.0-5.5", TestStatus.NORMAL),
                    TestItem("Bạch cầu", "8.2", "G/L", "4.0-10.0", TestStatus.NORMAL),
                    TestItem("Tiểu cầu", "250", "G/L", "150-400", TestStatus.NORMAL),
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

        val testResultAdapter = TestResultAdapter(testResults)
        rvTestResults.adapter = testResultAdapter
    }
}
