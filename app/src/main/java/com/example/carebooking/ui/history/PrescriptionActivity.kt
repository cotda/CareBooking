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

class PrescriptionActivity : AppCompatActivity() {

    private lateinit var rvPrescriptions: RecyclerView
    private lateinit var btnBack: ImageView
    private lateinit var btnDownload: View
    private lateinit var btnPrint: View
    private lateinit var btnShare: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        // Initialize views
        rvPrescriptions = findViewById(R.id.rvPrescriptions)
        btnBack = findViewById(R.id.btnBack)
        btnDownload = findViewById(R.id.btnDownload)
        btnPrint = findViewById(R.id.btnPrint)
        btnShare = findViewById(R.id.btnShare)

        // Setup RecyclerView
        rvPrescriptions.layoutManager = LinearLayoutManager(this)
        rvPrescriptions.adapter = PrescriptionAdapter(getSampleData())

        // Setup click listeners
        btnBack.setOnClickListener { finish() }
        btnDownload.setOnClickListener {
            Toast.makeText(this, "Đang tải xuống...", Toast.LENGTH_SHORT).show()
        }
        btnPrint.setOnClickListener {
            Toast.makeText(this, "Đang in...", Toast.LENGTH_SHORT).show()
        }
        btnShare.setOnClickListener {
            Toast.makeText(this, "Chia sẻ đơn thuốc", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSampleData(): List<Prescription> {
        return listOf(
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
    }

    // Adapter for prescriptions
    private inner class PrescriptionAdapter(private val prescriptions: List<Prescription>) :
        RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

        inner class PrescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvNumber: TextView = view.findViewById(R.id.tvNumber)
            val tvMedicineName: TextView = view.findViewById(R.id.tvMedicineName)
            val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
            val tvDosage: TextView = view.findViewById(R.id.tvDosage)
            val tvDuration: TextView = view.findViewById(R.id.tvDuration)
            val tvUsage: TextView = view.findViewById(R.id.tvUsage)
            val layoutNotes: LinearLayout = view.findViewById(R.id.layoutNotes)
            val tvNotes: TextView = view.findViewById(R.id.tvNotes)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prescription, parent, false)
            return PrescriptionViewHolder(view)
        }

        override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
            val prescription = prescriptions[position]
            
            holder.tvNumber.text = (position + 1).toString()
            holder.tvMedicineName.text = prescription.name
            holder.tvQuantity.text = prescription.quantity
            holder.tvDosage.text = "${prescription.dosage} × ${prescription.frequency}"
            holder.tvDuration.text = prescription.duration
            holder.tvUsage.text = prescription.usage

            // Show/hide notes
            if (prescription.notes != null && prescription.notes.isNotEmpty()) {
                holder.layoutNotes.visibility = View.VISIBLE
                holder.tvNotes.text = prescription.notes
            } else {
                holder.layoutNotes.visibility = View.GONE
            }
        }

        override fun getItemCount() = prescriptions.size
    }
}
