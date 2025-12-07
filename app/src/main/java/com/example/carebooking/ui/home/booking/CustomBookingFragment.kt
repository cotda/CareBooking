package com.example.carebooking.ui.home.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.carebooking.R
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class CustomBookingFragment : Fragment() {

    private lateinit var spinnerSpecialty: Spinner
    private lateinit var spinnerDisease: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var timeSlotContainer: GridLayout
    private lateinit var etNotes: EditText
    private lateinit var btnConfirmBooking: MaterialButton

    private var selectedDate: Date? = null
    private var selectedTime: String? = null

    private val specialties = mapOf(
        "Chọn chuyên khoa" to "default",
        "❤️ Tim mạch" to "cardiology",
        "🩺 Nội tổng quát" to "internal",
        "👶 Nhi khoa" to "pediatrics",
        "💊 Da liễu" to "dermatology",
        "🦴 Cơ xương khớp" to "orthopedics",
        "🧠 Thần kinh" to "neurology",
        "🫁 Tiêu hóa" to "gastroenterology",
        "👂 Tai mũi họng" to "ent"
    )

    private val diseasesBySpecialty = mapOf(
        "cardiology" to listOf("Tăng huyết áp", "Rối loạn nhịp tim", "Suy tim", "Bệnh mạch vành", "Khác"),
        "internal" to listOf("Đái tháo đường", "Viêm gan", "Suy thận", "Bệnh phổi", "Khác"),
        "pediatrics" to listOf("Sốt virus", "Viêm phổi", "Tiêu chảy", "Suy dinh dưỡng", "Khác"),
        "dermatology" to listOf("Mụn trứng cá", "Viêm da", "Nấm da", "Dị ứng da", "Khác"),
        "orthopedics" to listOf("Đau lưng", "Thoái hóa khớp", "Viêm khớp", "Gãy xương", "Khác"),
        "neurology" to listOf("Đau đầu", "Chóng mặt", "Mất ngủ", "Đột quỵ", "Khác"),
        "gastroenterology" to listOf("Viêm dạ dày", "Trào ngược", "Loét dạ dày", "Viêm đại tràng", "Khác"),
        "ent" to listOf("Viêm amidan", "Viêm tai", "Viêm xoang", "Viêm họng", "Khác")
    )

    private val timeSlots = listOf(
        "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
        "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_custom_booking, container, false)

        initViews(view)
        setupSpecialtySpinner()
        setupDatePicker()
        setupConfirmButton()

        return view
    }

    private fun initViews(view: View) {
        spinnerSpecialty = view.findViewById(R.id.spinnerSpecialty)
        spinnerDisease = view.findViewById(R.id.spinnerDisease)
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate)
        timeSlotContainer = view.findViewById(R.id.timeSlotContainer)
        etNotes = view.findViewById(R.id.etNotes)
        btnConfirmBooking = view.findViewById(R.id.btnConfirmBooking)
    }

    private fun setupSpecialtySpinner() {
        val specialtyNames = specialties.keys.toList()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            specialtyNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSpecialty.adapter = adapter

        spinnerSpecialty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSpecialtyName = specialtyNames[position]
                val specialtyKey = specialties[selectedSpecialtyName]

                if (specialtyKey != null && specialtyKey != "default") {
                    setupDiseaseSpinner(specialtyKey)
                    spinnerDisease.visibility = View.VISIBLE
                } else {
                    spinnerDisease.visibility = View.GONE
                    timeSlotContainer.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupDiseaseSpinner(specialtyKey: String) {
        val diseases = diseasesBySpecialty[specialtyKey] ?: emptyList()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            diseases
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDisease.adapter = adapter
    }

    private fun setupDatePicker() {
        tvSelectedDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.time
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    tvSelectedDate.text = dateFormat.format(selectedDate!!)
                    tvSelectedDate.setTextColor(resources.getColor(R.color.black, null))
                    
                    // Show time slots after date is selected
                    setupTimeSlots()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            
            // Disable past dates
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun setupTimeSlots() {
        timeSlotContainer.removeAllViews()
        timeSlotContainer.visibility = View.VISIBLE

        timeSlots.forEach { time ->
            val button = MaterialButton(
                requireContext(),
                null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle
            )
            
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8)
            }
            
            button.layoutParams = params
            button.text = time
            button.textSize = 13f
            button.setPadding(0, 24, 0, 24)
            
            button.setOnClickListener {
                selectedTime = time
                updateTimeSlotButtons(button)
            }
            
            timeSlotContainer.addView(button)
        }
    }

    private fun updateTimeSlotButtons(selectedButton: MaterialButton) {
        for (i in 0 until timeSlotContainer.childCount) {
            val button = timeSlotContainer.getChildAt(i) as MaterialButton
            if (button == selectedButton) {
                button.setBackgroundColor(resources.getColor(R.color.blue_600, null))
                button.setTextColor(resources.getColor(R.color.white, null))
            } else {
                button.setBackgroundColor(resources.getColor(R.color.gray_100, null))
                button.setTextColor(resources.getColor(R.color.gray_dark, null))
            }
        }
    }

    private fun setupConfirmButton() {
        btnConfirmBooking.setOnClickListener {
            if (validateForm()) {
                showBookingConfirmation()
            }
        }
    }

    private fun validateForm(): Boolean {
        val specialtyPosition = spinnerSpecialty.selectedItemPosition
        
        if (specialtyPosition == 0) {
            Toast.makeText(context, "Vui lòng chọn chuyên khoa", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (selectedDate == null) {
            Toast.makeText(context, "Vui lòng chọn ngày khám", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (selectedTime == null) {
            Toast.makeText(context, "Vui lòng chọn giờ khám", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }

    private fun showBookingConfirmation() {
        // TODO: Show confirmation dialog
        val specialtyName = spinnerSpecialty.selectedItem.toString()
        val disease = if (spinnerDisease.visibility == View.VISIBLE) {
            spinnerDisease.selectedItem.toString()
        } else {
            "Không xác định"
        }
        val notes = etNotes.text.toString()
        
        Toast.makeText(
            context,
            "Đặt lịch: $specialtyName\nTriệu chứng: $disease\nNgày: ${tvSelectedDate.text}\nGiờ: $selectedTime",
            Toast.LENGTH_LONG
        ).show()
    }
}
