@file:Suppress("SameParameterValue")

package com.example.learning.ui.slideshow

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.R
import com.example.learning.R.*
import com.example.learning.R.string.*
import com.example.learning.databinding.FragmentUpgradeBinding
import com.example.learning.ui.viewBinding

class UpgradeFragment : Fragment(layout.fragment_upgrade) {
    private val upgradeModelList = mutableListOf<UpgradeModel>()

    private val binding by viewBinding(FragmentUpgradeBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        var balance = sharedPreferences.getInt("balance", 0)

        binding.rvRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        var currentMultiplier = sharedPreferences.getInt("multiplier", 0)
        binding.rvRecyclerView.adapter = UpgradeAdapter(balance, upgradeModelList) { model ->
            currentMultiplier += model.multiplier
            sharedPreferences.edit().putInt("multiplier", currentMultiplier).apply()
            balance -= model.cost
            sharedPreferences.edit().putInt("balance", balance).apply()
        }
    }

    private fun addToList(title: String, description: String, image: Int, cost: Int, multiplier: Int) {
        upgradeModelList.add(UpgradeModel(title, description, image, cost, multiplier))
    }

    private fun postToList() {
        addToList("Upgrade", "+1", R.mipmap.ic_launcher, 50, 1)
        addToList("Upgrade", "+2", R.mipmap.ic_launcher, 100, 2)
        addToList("Upgrade", "+4", R.mipmap.ic_launcher, 300, 4)
        addToList("Upgrade", "+6", R.mipmap.ic_launcher, 800, 6)
    }
}