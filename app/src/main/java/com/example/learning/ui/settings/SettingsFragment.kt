package com.example.learning.ui.settings


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learning.R
import com.example.learning.databinding.FragmentSettingsBinding
import com.example.learning.ui.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val CHANNEL_ID = "channel_id"
    private val notificationID = 101
    private val sharedPreferences by lazy {
        activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding.button.isEnabled = false
        updateButtonState()
        binding.btnSwitch.isChecked = sharedPreferences?.getBoolean("switch_state", false) ?: false
        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences?.edit()?.putBoolean("switch_state", isChecked)?.apply()
            binding.button.isEnabled = isChecked
            createNotificationChannel()
            binding.button.setOnClickListener {
                sendNotification()
            }
            updateButtonState()
        }
    }
    override fun onResume() {
        super.onResume()
        updateButtonState()
        if (binding.btnSwitch.isChecked) {
            binding.button.setOnClickListener {
                sendNotification()
            }
        }
    }

    private fun updateButtonState() {
        binding.button.isEnabled = binding.btnSwitch.isChecked
    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description=descriptionText
            }
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(){

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Title")
            .setContentText("aksa pglu")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationID, builder.build())
        }
    }
}