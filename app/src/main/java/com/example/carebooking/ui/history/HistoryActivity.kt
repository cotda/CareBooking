package com.example.carebooking.ui.history

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.example.carebooking.ui.common.setupBottomNavigation

class HistoryActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tabLayout: TabLayout
    private lateinit var allContainer: NestedScrollView
    private lateinit var upcomingOnlyList: RecyclerView
    private lateinit var completedOnlyList: RecyclerView
    private lateinit var allUpcomingList: RecyclerView
    private lateinit var allCompletedList: RecyclerView

    private val upcomingAdapterAll by lazy { UpcomingAppointmentsAdapter(::onAppointmentDetailsClick) }
    private val upcomingAdapterOnly by lazy { UpcomingAppointmentsAdapter(::onAppointmentDetailsClick) }
    private val recordAdapterAll by lazy {
        MedicalRecordAdapter(
            onPrescriptionClick = ::onPrescriptionClick,
            onResultsClick = ::onResultsClick,
            onViewClick = ::onRecordViewClick
        )
    }
    private val recordAdapterOnly by lazy {
        MedicalRecordAdapter(
            onPrescriptionClick = ::onPrescriptionClick,
            onResultsClick = ::onResultsClick,
            onViewClick = ::onRecordViewClick
        )
    }

    private val upcomingAppointments = listOf(
        UpcomingAppointment(
            id = 1,
            date = "15/11/2024",
            time = "14:30",
            doctor = "BS. Nguyễn Văn A",
            specialty = "Tim mạch",
            hospital = "Bệnh viện Đại học Y",
            status = AppointmentStatus.CONFIRMED
        ),
        UpcomingAppointment(
            id = 2,
            date = "18/11/2024",
            time = "09:00",
            doctor = "BS. Trần Thị B",
            specialty = "Nội tổng quát",
            hospital = "Phòng khám Đa khoa Medcare",
            status = AppointmentStatus.PENDING
        )
    )

    private val medicalRecords = listOf(
        MedicalRecord(
            id = 1,
            date = "10/11/2024",
            doctor = "BS. Nguyễn Văn A",
            specialty = "Tim mạch",
            diagnosis = "Khám sức khỏe định kỳ",
            hospital = "Bệnh viện Đại học Y",
            prescriptionAvailable = true,
            testResultsAvailable = true
        ),
        MedicalRecord(
            id = 2,
            date = "05/11/2024",
            doctor = "BS. Trần Thị B",
            specialty = "Nội tổng quát",
            diagnosis = "Viêm dạ dày",
            hospital = "Phòng khám Đa khoa Medcare",
            prescriptionAvailable = true,
            testResultsAvailable = false
        ),
        MedicalRecord(
            id = 3,
            date = "28/10/2024",
            doctor = "BS. Lê Minh C",
            specialty = "Da liễu",
            diagnosis = "Dị ứng da",
            hospital = "Bệnh viện Da liễu TW",
            prescriptionAvailable = true,
            testResultsAvailable = true
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        bottomNav = findViewById(R.id.bottomNav)
        setupBottomNavigation(bottomNav, R.id.nav_history)

        setupViews()
        setupTabs()
        bindAdapters()
        bindActions()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_history
    }

    private fun setupViews() {
        tabLayout = findViewById(R.id.historyTabLayout)
        allContainer = findViewById(R.id.historyAllContainer)
        upcomingOnlyList = findViewById(R.id.rvHistoryUpcomingOnly)
        completedOnlyList = findViewById(R.id.rvHistoryCompletedOnly)
        allUpcomingList = findViewById(R.id.rvHistoryAllUpcoming)
        allCompletedList = findViewById(R.id.rvHistoryAllCompleted)

        allUpcomingList.layoutManager = LinearLayoutManager(this)
        allUpcomingList.adapter = upcomingAdapterAll
        allUpcomingList.isNestedScrollingEnabled = false

        allCompletedList.layoutManager = LinearLayoutManager(this)
        allCompletedList.adapter = recordAdapterAll
        allCompletedList.isNestedScrollingEnabled = false

        upcomingOnlyList.layoutManager = LinearLayoutManager(this)
        upcomingOnlyList.adapter = upcomingAdapterOnly

        completedOnlyList.layoutManager = LinearLayoutManager(this)
        completedOnlyList.adapter = recordAdapterOnly
    }

    private fun setupTabs() {
        tabLayout.apply {
            addTab(newTab().setText(R.string.history_tab_all), true)
            addTab(newTab().setText(R.string.history_tab_upcoming))
            addTab(newTab().setText(R.string.history_tab_completed))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> showAll()
                        1 -> showUpcoming()
                        2 -> showCompleted()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
        }

        showAll()
    }

    private fun bindAdapters() {
        upcomingAdapterAll.submitList(upcomingAppointments)
        upcomingAdapterOnly.submitList(upcomingAppointments)
        recordAdapterAll.submitList(medicalRecords)
        recordAdapterOnly.submitList(medicalRecords)
    }

    private fun bindActions() {
        findViewById<MaterialButton>(R.id.btnHistoryFilter).setOnClickListener {
            Toast.makeText(this, R.string.history_filter, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAll() {
        allContainer.isVisible = true
        upcomingOnlyList.isVisible = false
        completedOnlyList.isVisible = false
    }

    private fun showUpcoming() {
        allContainer.isVisible = false
        upcomingOnlyList.isVisible = true
        completedOnlyList.isVisible = false
    }

    private fun showCompleted() {
        allContainer.isVisible = false
        upcomingOnlyList.isVisible = false
        completedOnlyList.isVisible = true
    }

    private fun onAppointmentDetailsClick(appointment: UpcomingAppointment) {
        Toast.makeText(
            this,
            getString(R.string.history_details_placeholder, appointment.doctor),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onPrescriptionClick(record: MedicalRecord) {
        Toast.makeText(
            this,
            getString(R.string.history_prescription_placeholder, record.doctor),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onResultsClick(record: MedicalRecord) {
        Toast.makeText(
            this,
            getString(R.string.history_results_placeholder, record.doctor),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onRecordViewClick(record: MedicalRecord) {
        Toast.makeText(
            this,
            getString(R.string.history_record_placeholder, record.doctor),
            Toast.LENGTH_SHORT
        ).show()
    }
}

private data class UpcomingAppointment(
    val id: Int,
    val date: String,
    val time: String,
    val doctor: String,
    val specialty: String,
    val hospital: String,
    val status: AppointmentStatus
)

private enum class AppointmentStatus {
    CONFIRMED,
    PENDING
}

private data class MedicalRecord(
    val id: Int,
    val date: String,
    val doctor: String,
    val specialty: String,
    val diagnosis: String,
    val hospital: String,
    val prescriptionAvailable: Boolean,
    val testResultsAvailable: Boolean
)

private class UpcomingAppointmentsAdapter(
    private val onDetailsClick: (UpcomingAppointment) -> Unit
) : RecyclerView.Adapter<UpcomingAppointmentsAdapter.UpcomingViewHolder>() {

    private val items = mutableListOf<UpcomingAppointment>()

    fun submitList(data: List<UpcomingAppointment>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_upcoming, parent, false)
        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(items[position], onDetailsClick)
    }

    override fun getItemCount(): Int = items.size

    class UpcomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconContainer: FrameLayout = itemView.findViewById(R.id.historyUpcomingIconContainer)
        private val doctor: TextView = itemView.findViewById(R.id.tvHistoryUpcomingDoctor)
        private val specialty: TextView = itemView.findViewById(R.id.tvHistoryUpcomingSpecialty)
        private val date: TextView = itemView.findViewById(R.id.tvHistoryUpcomingDate)
        private val time: TextView = itemView.findViewById(R.id.tvHistoryUpcomingTime)
        private val hospital: TextView = itemView.findViewById(R.id.tvHistoryUpcomingHospital)
        private val status: TextView = itemView.findViewById(R.id.tvHistoryUpcomingStatus)
        private val btnDetails: MaterialButton = itemView.findViewById(R.id.btnHistoryUpcomingDetails)

        fun bind(item: UpcomingAppointment, onDetailsClick: (UpcomingAppointment) -> Unit) {
            val context = itemView.context
            doctor.text = item.doctor
            specialty.text = item.specialty
            date.text = context.getString(R.string.history_date_format, item.date)
            time.text = context.getString(R.string.history_time_format, item.time)
            hospital.text = context.getString(R.string.history_hospital_format, item.hospital)

            ViewCompat.setBackgroundTintList(
                iconContainer,
                ColorStateList.valueOf(context.colorCompat(R.color.blue_600))
            )

            val (text, backgroundColor, textColor) = when (item.status) {
                AppointmentStatus.CONFIRMED -> Triple(
                    context.getString(R.string.history_status_confirmed),
                    R.color.green_100,
                    R.color.green_600
                )

                AppointmentStatus.PENDING -> Triple(
                    context.getString(R.string.history_status_pending),
                    R.color.yellow_100,
                    R.color.yellow_warning
                )
            }

            status.text = text
            status.setTextColor(context.colorCompat(textColor))
            ViewCompat.setBackgroundTintList(
                status,
                ColorStateList.valueOf(context.colorCompat(backgroundColor))
            )

            btnDetails.setOnClickListener { onDetailsClick(item) }
        }
    }
}

private class MedicalRecordAdapter(
    private val onPrescriptionClick: (MedicalRecord) -> Unit,
    private val onResultsClick: (MedicalRecord) -> Unit,
    private val onViewClick: (MedicalRecord) -> Unit
) : RecyclerView.Adapter<MedicalRecordAdapter.MedicalRecordViewHolder>() {

    private val items = mutableListOf<MedicalRecord>()

    fun submitList(data: List<MedicalRecord>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_record, parent, false)
        return MedicalRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicalRecordViewHolder, position: Int) {
        holder.bind(items[position], onPrescriptionClick, onResultsClick, onViewClick)
    }

    override fun getItemCount(): Int = items.size

    class MedicalRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val card: MaterialCardView = itemView as MaterialCardView
        private val doctor: TextView = itemView.findViewById(R.id.tvHistoryRecordDoctor)
        private val date: TextView = itemView.findViewById(R.id.tvHistoryRecordDate)
        private val specialty: TextView = itemView.findViewById(R.id.tvHistoryRecordSpecialty)
        private val diagnosis: TextView = itemView.findViewById(R.id.tvHistoryRecordDiagnosis)
        private val hospital: TextView = itemView.findViewById(R.id.tvHistoryRecordHospital)
        private val btnPrescription: MaterialButton = itemView.findViewById(R.id.btnHistoryRecordPrescription)
        private val btnResults: MaterialButton = itemView.findViewById(R.id.btnHistoryRecordResults)
        private val btnView: MaterialButton = itemView.findViewById(R.id.btnHistoryRecordView)

        fun bind(
            item: MedicalRecord,
            onPrescriptionClick: (MedicalRecord) -> Unit,
            onResultsClick: (MedicalRecord) -> Unit,
            onViewClick: (MedicalRecord) -> Unit
        ) {
            val context = itemView.context
            doctor.text = item.doctor
            date.text = item.date
            specialty.text = item.specialty
            diagnosis.text = item.diagnosis
            hospital.text = context.getString(R.string.history_hospital_format, item.hospital)

            btnPrescription.isVisible = item.prescriptionAvailable
            btnResults.isVisible = item.testResultsAvailable

            btnPrescription.setOnClickListener { onPrescriptionClick(item) }
            btnResults.setOnClickListener { onResultsClick(item) }
            btnView.setOnClickListener { onViewClick(item) }

            // ensure stroke color remains consistent when items are recycled
            card.strokeColor = context.colorCompat(R.color.gray_light)
        }
    }
}

private fun Context.colorCompat(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)


