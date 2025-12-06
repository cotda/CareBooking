package com.example.carebooking.ui.profile

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.carebooking.BuildConfig
import com.example.carebooking.R
import com.example.carebooking.ui.common.setupBottomNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class ProfileActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private val menuItems = listOf(
        ProfileMenuItem(R.id.menuPersonalInfo, ProfileMenuAction.PERSONAL_INFO),
        ProfileMenuItem(R.id.menuHealthRecord, ProfileMenuAction.HEALTH_RECORD),
        ProfileMenuItem(R.id.menuSettings, ProfileMenuAction.SETTINGS),
        ProfileMenuItem(R.id.menuHelp, ProfileMenuAction.HELP)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bottomNav = findViewById(R.id.bottomNav)
        setupBottomNavigation(bottomNav, R.id.nav_account)

        val profileInfo = ProfileInfo(
            fullName = intent.getStringExtra(EXTRA_FULL_NAME) ?: DEFAULT_FULL_NAME,
            memberId = intent.getStringExtra(EXTRA_MEMBER_ID) ?: DEFAULT_MEMBER_ID,
            phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER) ?: DEFAULT_PHONE,
            email = intent.getStringExtra(EXTRA_EMAIL) ?: DEFAULT_EMAIL,
            birthday = intent.getStringExtra(EXTRA_BIRTHDAY) ?: DEFAULT_BIRTHDAY,
            address = intent.getStringExtra(EXTRA_ADDRESS) ?: DEFAULT_ADDRESS
        )

        bindProfile(profileInfo)
        bindMenuActions()
        bindButtons()
        bindVersionLabel()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_account
    }

    private fun bindProfile(info: ProfileInfo) {
        with(info) {
            findViewById<TextView>(R.id.tvAvatarInitials).text = fullName.toInitials()
            findViewById<TextView>(R.id.tvUserName).text = fullName
            findViewById<TextView>(R.id.tvUserId).text =
                getString(R.string.profile_member_id_format, memberId)
            findViewById<TextView>(R.id.tvPhone).text = phoneNumber
            findViewById<TextView>(R.id.tvEmail).text = email
            findViewById<TextView>(R.id.tvBirthday).text = birthday
            findViewById<TextView>(R.id.tvAddress).text = address
        }
    }

    private fun bindMenuActions() {
        menuItems.forEach { item ->
            findViewById<LinearLayout>(item.viewId).setOnClickListener {
                handleMenuAction(item.action)
            }
        }
    }

    private fun bindButtons() {
        findViewById<ImageButton>(R.id.btnEditProfile).setOnClickListener {
            Toast.makeText(this, getString(R.string.profile_edit_coming_soon), Toast.LENGTH_SHORT)
                .show()
        }

        findViewById<MaterialCardView>(R.id.cardLogout).setOnClickListener {
            handleLogout()
        }
    }

    private fun bindVersionLabel() {
        val versionText = getString(R.string.profile_version, BuildConfig.VERSION_NAME)
        findViewById<TextView>(R.id.tvAppVersion).text = versionText
    }

    private fun handleMenuAction(action: ProfileMenuAction) {
        val message = when (action) {
            ProfileMenuAction.PERSONAL_INFO -> getString(R.string.profile_menu_personal_info)
            ProfileMenuAction.HEALTH_RECORD -> getString(R.string.profile_menu_health_record)
            ProfileMenuAction.SETTINGS -> getString(R.string.profile_menu_settings)
            ProfileMenuAction.HELP -> getString(R.string.profile_menu_help)
        }

        Toast.makeText(this, getString(R.string.profile_menu_placeholder, message), Toast.LENGTH_SHORT)
            .show()
    }

    private fun handleLogout() {
        Toast.makeText(this, getString(R.string.profile_logged_out), Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }

    private fun String.toInitials(): String {
        if (isBlank()) return "--"
        return trim()
            .split("\\s+".toRegex())
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
            .takeIf { it.isNotBlank() } ?: "--"
    }

    private data class ProfileInfo(
        val fullName: String,
        val memberId: String,
        val phoneNumber: String,
        val email: String,
        val birthday: String,
        val address: String
    )

    private data class ProfileMenuItem(
        val viewId: Int,
        val action: ProfileMenuAction
    )

    private enum class ProfileMenuAction {
        PERSONAL_INFO,
        HEALTH_RECORD,
        SETTINGS,
        HELP
    }

    companion object {
        const val EXTRA_FULL_NAME = "extra_full_name"
        const val EXTRA_MEMBER_ID = "extra_member_id"
        const val EXTRA_PHONE_NUMBER = "extra_phone_number"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_BIRTHDAY = "extra_birthday"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_VISIT_COUNT = "extra_visit_count"
        const val EXTRA_APPOINTMENT_COUNT = "extra_appointment_count"
        const val EXTRA_PRESCRIPTION_COUNT = "extra_prescription_count"

        private const val DEFAULT_FULL_NAME = "Nguyễn Văn An"
        private const val DEFAULT_MEMBER_ID = "MED123456"
        private const val DEFAULT_PHONE = "0123 456 789"
        private const val DEFAULT_EMAIL = "nguyenvanan@email.com"
        private const val DEFAULT_BIRTHDAY = "01/01/1990"
        private const val DEFAULT_ADDRESS = "Hà Nội, Việt Nam"
        private const val DEFAULT_VISIT_COUNT = 15
        private const val DEFAULT_APPOINTMENT_COUNT = 3
        private const val DEFAULT_PRESCRIPTION_COUNT = 8
    }
}

