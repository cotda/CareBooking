package com.example.carebooking.ui.notifications

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.widget.ImageViewCompat
import com.example.carebooking.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.example.carebooking.ui.common.setupBottomNavigation

class NotificationActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tabLayout: TabLayout
    private lateinit var allRecyclerView: RecyclerView
    private lateinit var reminderContainer: NestedScrollView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var pastRecyclerView: RecyclerView

    private val allAdapter by lazy { NotificationAdapter() }
    private val pastAdapter by lazy { NotificationAdapter(showUnreadIndicator = false) }
    private val upcomingAdapter by lazy {
        ReminderUpcomingAdapter(
            onDetailsClick = { reminder ->
                Toast.makeText(
                    this,
                    getString(R.string.notifications_detail_placeholder, reminder.title),
                    Toast.LENGTH_SHORT
                ).show()
            },
            onSnoozeClick = { reminder ->
                Toast.makeText(
                    this,
                    getString(R.string.notifications_snooze_placeholder, reminder.title),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    private val notifications = mutableListOf(
        NotificationItem(
            id = 1,
            type = NotificationType.REMINDER,
            title = "Nhắc lịch khám",
            message = "Bạn có lịch khám với BS. Nguyễn Văn A vào 14:30 ngày mai",
            time = "2 giờ trước",
            read = false,
            iconRes = R.drawable.ic_bell,
            iconBackgroundColorRes = R.color.notification_blue_icon_bg,
            iconTintRes = R.color.blue_600
        ),
        NotificationItem(
            id = 2,
            type = NotificationType.CONFIRMED,
            title = "Xác nhận lịch hẹn",
            message = "Lịch khám của bạn với BS. Trần Thị B đã được xác nhận",
            time = "5 giờ trước",
            read = false,
            iconRes = R.drawable.ic_check_circle,
            iconBackgroundColorRes = R.color.notification_green_icon_bg,
            iconTintRes = R.color.green_600
        ),
        NotificationItem(
            id = 3,
            type = NotificationType.RESULT,
            title = "Kết quả xét nghiệm",
            message = "Kết quả xét nghiệm máu của bạn đã sẵn sàng. Nhấn để xem chi tiết",
            time = "1 ngày trước",
            read = true,
            iconRes = R.drawable.ic_file,
            iconBackgroundColorRes = R.color.notification_purple_icon_bg,
            iconTintRes = R.color.purple_600
        ),
        NotificationItem(
            id = 4,
            type = NotificationType.REMINDER,
            title = "Nhắc uống thuốc",
            message = "Đã đến giờ uống thuốc theo đơn của BS. Nguyễn Văn A",
            time = "1 ngày trước",
            read = true,
            iconRes = R.drawable.ic_clock,
            iconBackgroundColorRes = R.color.notification_orange_icon_bg,
            iconTintRes = R.color.orange_600
        ),
        NotificationItem(
            id = 5,
            type = NotificationType.CANCELLED,
            title = "Lịch hẹn bị hủy",
            message = "Lịch khám ngày 20/11 đã bị hủy. Vui lòng đặt lịch mới",
            time = "2 ngày trước",
            read = true,
            iconRes = R.drawable.ic_close,
            iconBackgroundColorRes = R.color.notification_red_icon_bg,
            iconTintRes = R.color.red_500
        )
    )

    private val upcomingReminders = listOf(
        ReminderItem(
            id = 1,
            title = "Khám tim mạch định kỳ",
            date = "📅 15/11/2024",
            time = "🕐 14:30",
            doctor = "BS. Nguyễn Văn A"
        ),
        ReminderItem(
            id = 2,
            title = "Tái khám nội tổng quát",
            date = "📅 18/11/2024",
            time = "🕐 09:00",
            doctor = "BS. Trần Thị B"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        bottomNav = findViewById(R.id.bottomNav)
        setupBottomNavigation(bottomNav, R.id.nav_notifications)

        tabLayout = findViewById(R.id.tabLayout)
        allRecyclerView = findViewById(R.id.rvAllNotifications)
        reminderContainer = findViewById(R.id.reminderContainer)
        upcomingRecyclerView = findViewById(R.id.rvUpcomingReminders)
        pastRecyclerView = findViewById(R.id.rvPastReminders)

        setupTabs()
        setupAllNotificationsList()
        setupReminderLists()
        bindHeaderActions()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_notifications
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.notifications_tab_all), true)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.notifications_tab_reminders))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showAllNotifications()
                    1 -> showReminders()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        showAllNotifications()
    }

    private fun setupAllNotificationsList() {
        allRecyclerView.layoutManager = LinearLayoutManager(this)
        allRecyclerView.adapter = allAdapter
        allRecyclerView.setHasFixedSize(false)
        allRecyclerView.setItemViewCacheSize(8)
        allAdapter.submitList(notifications)
    }

    private fun setupReminderLists() {
        upcomingRecyclerView.layoutManager = LinearLayoutManager(this)
        upcomingRecyclerView.adapter = upcomingAdapter
        upcomingRecyclerView.isNestedScrollingEnabled = false
        upcomingAdapter.submitList(upcomingReminders)

        pastRecyclerView.layoutManager = LinearLayoutManager(this)
        pastRecyclerView.adapter = pastAdapter
        pastRecyclerView.isNestedScrollingEnabled = false
        pastAdapter.submitList(getPastReminders())
    }

    private fun bindHeaderActions() {
        findViewById<MaterialButton>(R.id.btnMarkAllRead).setOnClickListener {
            notifications.forEach { it.read = true }
            allAdapter.submitList(notifications.toList())
            pastAdapter.submitList(getPastReminders())
            Toast.makeText(this, getString(R.string.notifications_marked_all_read), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAllNotifications() {
        allRecyclerView.visibility = View.VISIBLE
        reminderContainer.visibility = View.GONE
    }

    private fun showReminders() {
        allRecyclerView.visibility = View.GONE
        reminderContainer.visibility = View.VISIBLE
        pastAdapter.submitList(getPastReminders())
    }

    private fun getPastReminders(): List<NotificationItem> {
        return notifications.filter { it.type == NotificationType.REMINDER && it.read }
    }
}

private data class NotificationItem(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val message: String,
    val time: String,
    var read: Boolean,
    val iconRes: Int,
    val iconBackgroundColorRes: Int,
    val iconTintRes: Int
)

private enum class NotificationType {
    REMINDER,
    CONFIRMED,
    RESULT,
    MEDICATION,
    CANCELLED
}

private data class ReminderItem(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val doctor: String
)

private class NotificationAdapter(
    private val showUnreadIndicator: Boolean = true
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private val items = mutableListOf<NotificationItem>()

    fun submitList(data: List<NotificationItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(items[position], showUnreadIndicator)
    }

    override fun getItemCount(): Int = items.size

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val card: MaterialCardView = itemView as MaterialCardView
        private val iconContainer: FrameLayout = itemView.findViewById(R.id.iconContainer)
        private val icon: android.widget.ImageView = itemView.findViewById(R.id.ivIcon)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val message: TextView = itemView.findViewById(R.id.tvMessage)
        private val time: TextView = itemView.findViewById(R.id.tvTime)
        private val indicator: View = itemView.findViewById(R.id.viewUnreadIndicator)

        fun bind(item: NotificationItem, showIndicator: Boolean) {
            val context = itemView.context
            title.text = item.title
            message.text = item.message
            time.text = item.time

            icon.setImageResource(item.iconRes)
            ImageViewCompat.setImageTintList(
                icon,
                ColorStateList.valueOf(context.getColorCompat(item.iconTintRes))
            )
            ViewCompat.setBackgroundTintList(
                iconContainer,
                ColorStateList.valueOf(context.getColorCompat(item.iconBackgroundColorRes))
            )

            if (item.read) {
                card.setCardBackgroundColor(context.getColorCompat(android.R.color.white))
                card.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.notification_read_stroke)
                card.strokeColor = context.getColorCompat(R.color.gray_300)
                indicator.visibility = View.GONE
            } else {
                card.setCardBackgroundColor(context.getColorCompat(R.color.blue_100))
                card.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.notification_unread_stroke)
                card.strokeColor = context.getColorCompat(R.color.blue_200)
                indicator.visibility = if (showIndicator) View.VISIBLE else View.GONE
            }
        }
    }
}

private class ReminderUpcomingAdapter(
    private val onDetailsClick: (ReminderItem) -> Unit,
    private val onSnoozeClick: (ReminderItem) -> Unit
) : RecyclerView.Adapter<ReminderUpcomingAdapter.ReminderViewHolder>() {

    private val items = mutableListOf<ReminderItem>()

    fun submitList(data: List<ReminderItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder_upcoming, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(items[position], onDetailsClick, onSnoozeClick)
    }

    override fun getItemCount(): Int = items.size

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconContainer: FrameLayout = itemView.findViewById(R.id.reminderIconContainer)
        private val title: TextView = itemView.findViewById(R.id.tvReminderTitle)
        private val doctor: TextView = itemView.findViewById(R.id.tvReminderDoctor)
        private val date: TextView = itemView.findViewById(R.id.tvReminderDate)
        private val time: TextView = itemView.findViewById(R.id.tvReminderTime)
        private val btnDetails: MaterialButton = itemView.findViewById(R.id.btnReminderDetails)
        private val btnSnooze: MaterialButton = itemView.findViewById(R.id.btnReminderSnooze)

        fun bind(
            item: ReminderItem,
            onDetailsClick: (ReminderItem) -> Unit,
            onSnoozeClick: (ReminderItem) -> Unit
        ) {
            val context = itemView.context
            title.text = item.title
            doctor.text = item.doctor
            date.text = item.date
            time.text = item.time

            ViewCompat.setBackgroundTintList(
                iconContainer,
                ColorStateList.valueOf(context.getColorCompat(R.color.blue_600))
            )

            btnDetails.setOnClickListener { onDetailsClick(item) }
            btnSnooze.setOnClickListener { onSnoozeClick(item) }
        }
    }
}

private fun Context.getColorCompat(colorRes: Int): Int = androidx.core.content.ContextCompat.getColor(this, colorRes)

