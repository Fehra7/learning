package com.example.learning.ui.slideshow

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.R

@Suppress("DEPRECATION")
class UpgradeAdapter(
    private var balance: Int,
    private var upgradeModelList: List<UpgradeModel>,
    private val onUpgrade: (UpgradeModel) -> Unit
) :
    RecyclerView.Adapter<UpgradeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)
        val itemCost: TextView = itemView.findViewById(R.id.tv_cost)
        val isChecked: Boolean = false

        init {
            itemView.setOnClickListener { v: View ->
                val model: UpgradeModel = upgradeModelList[bindingAdapterPosition]
                if (balance >= model.cost) {
                    Toast.makeText(
                        itemView.context,
                        "You bought item # ${position + 1}",
                        Toast.LENGTH_SHORT
                    ).show()
                    onUpgrade(model)
                    balance -= model.cost
                } else {
                    Toast.makeText(
                        itemView.context,
                        "You don't have enough balance for this purchase!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return upgradeModelList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = upgradeModelList[position].title
        holder.itemDetail.text = upgradeModelList[position].description
        holder.itemPicture.setImageResource(upgradeModelList[position].image)
        holder.itemCost.text = upgradeModelList[position].cost.toString() + "$"
    }

}