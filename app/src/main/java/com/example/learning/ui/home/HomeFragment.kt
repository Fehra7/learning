package com.example.learning.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.learning.R
import com.example.learning.databinding.FragmentHomeBinding
import com.example.learning.ui.settings.SettingsViewModel
import com.example.learning.ui.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var touchCounter: TextView
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        count = sharedPreferences.getInt("count", 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        touchCounter = view.findViewById(R.id.touch_counter)
        touchCounter.text = count.toString()

        binding.root.setOnTouchListener  { _, _ ->
            count++
            touchCounter.text = count.toString()
            val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            sharedPreferences.edit().putInt("count", count).apply()
            true
        }
    }
}