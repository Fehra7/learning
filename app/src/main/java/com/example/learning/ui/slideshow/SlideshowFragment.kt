@file:Suppress("SameParameterValue")

package com.example.learning.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.R
import com.example.learning.R.*
import com.example.learning.R.string.*
import com.example.learning.databinding.FragmentSlideshowBinding
import com.example.learning.ui.home.HomeFragment
import com.example.learning.ui.settings.SettingsViewModel
import com.example.learning.ui.viewBinding

class SlideshowFragment : Fragment(layout.fragment_slideshow) {
    private val upgradeModelList = mutableListOf<UpgradeModel>()

    private val binding by viewBinding(FragmentSlideshowBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()

        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var currentMultiplier = sharedPreferences.getInt("multiplier", 0)
        recyclerView.adapter = MyAdapter(upgradeModelList) {multiplier ->
            currentMultiplier += multiplier
            sharedPreferences.edit().putInt("multiplier", currentMultiplier).apply()
        }
    }
    private fun addToList(title: String, description: String, image: Int){
        upgradeModelList.add(UpgradeModel(title, description, image))
    }
    private fun postToList(){
        for(i in 1..4){
            addToList("Upgrade", "+ $i", R.mipmap.ic_launcher)
        }
    }
}