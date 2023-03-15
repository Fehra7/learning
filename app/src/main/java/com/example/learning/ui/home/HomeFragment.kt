package com.example.learning.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learning.R
import com.example.learning.databinding.FragmentHomeBinding
import com.example.learning.ui.viewBinding

@Suppress("NAME_SHADOWING")
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var touchCounter: TextView
    private var balance = 0
    private var multiplier = 0

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        balance = sharedPreferences.getInt("balance", 0)
        multiplier = sharedPreferences.getInt("multiplier", 0)

        touchCounter = binding.balance
        touchCounter.text = "$balance $"

        binding.root.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    balance += multiplier + 1
                    touchCounter.text = "$balance $"
                    val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                    sharedPreferences.edit().putInt("balance", balance).apply()
                }
            }
            true
        }

    }
}
