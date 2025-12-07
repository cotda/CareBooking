package com.example.carebooking.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R

class PrescriptionFragment : Fragment() {

    private lateinit var rvPrescriptions: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prescription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView(view)
        loadSampleData()
    }

    private fun setupRecyclerView(view: View) {
        rvPrescriptions = view.findViewById(R.id.rvPrescriptions)
        rvPrescriptions.layoutManager = LinearLayoutManager(requireContext())
        rvPrescriptions.isNestedScrollingEnabled = false
    }

    private fun loadSampleData() {
        val prescriptions = listOf(
            Prescription(
                id = 1,
                name = "Amlodipine 5mg",
                dosage = "1 viên/lần",
                frequency = "1 lần/ngày",
                duration = "30 ngày",
                quantity = "30 viên",
                usage = "Uống vào buổi sáng sau ăn",
                notes = "Không bỏ sót liều"
            ),
            Prescription(
                id = 2,
                name = "Atorvastatin 10mg",
                dosage = "1 viên/lần",
                frequency = "1 lần/ngày",
                duration = "30 ngày",
                quantity = "30 viên",
                usage = "Uống vào buổi tối trước khi ngủ",
                notes = null
            ),
            Prescription(
                id = 3,
                name = "Vitamin B Complex",
                dosage = "1 viên/lần",
                frequency = "2 lần/ngày",
                duration = "30 ngày",
                quantity = "60 viên",
                usage = "Uống sau bữa ăn sáng và tối",
                notes = null
            )
        )

        val prescriptionAdapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_prescription, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val prescription = prescriptions[position]
                holder.itemView.apply {
                    findViewById<android.widget.TextView>(R.id.tvMedicineName).text = prescription.name
                    findViewById<android.widget.TextView>(R.id.tvDosage).text = "${prescription.dosage} × ${prescription.frequency}"
                    findViewById<android.widget.TextView>(R.id.tvDuration).text = prescription.duration
                    findViewById<android.widget.TextView>(R.id.tvUsage).text = prescription.usage
                    findViewById<android.widget.TextView>(R.id.tvQuantity).text = prescription.quantity
                    
                    val notesLayout = findViewById<View>(R.id.layoutNotes)
                    val tvNotes = findViewById<android.widget.TextView>(R.id.tvNotes)
                    if (prescription.notes != null && prescription.notes.isNotEmpty()) {
                        notesLayout.visibility = View.VISIBLE
                        tvNotes.text = prescription.notes
                    } else {
                        notesLayout.visibility = View.GONE
                    }
                }
            }

            override fun getItemCount() = prescriptions.size
        }
        rvPrescriptions.adapter = prescriptionAdapter
    }
}
