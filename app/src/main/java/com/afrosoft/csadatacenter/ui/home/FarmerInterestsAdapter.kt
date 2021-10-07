package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.R
import com.afrosoft.csadatacenter.databinding.SingleInterestLayoutBinding
import com.afrosoft.csadatacenter.models.Interest
import com.afrosoft.csadatacenter.network.NetworkClient
import com.bumptech.glide.Glide

class FarmerInterestsAdapter(val context: Context, val list: MutableList<Interest>, val func : (Interest?)->Unit): RecyclerView.Adapter<FarmerInterestsAdapter.FarmerInterestHolder>() {

    var selectedInterest : Interest? = list[0]

    inner class FarmerInterestHolder(val binding: SingleInterestLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun setData(interest: Interest) {
            binding.interestsBackground.setBackgroundResource(if (interest.id == selectedInterest?.id) R.drawable.black_filled_bg else R.drawable.black_border_bg )
            binding.tagName.setTextColor(if (interest.id == selectedInterest?.id) Color.parseColor("#FF388e3c") else Color.BLACK )
            binding.tagName.text = interest.plant_name

            Glide.with(context).asBitmap().load("${NetworkClient().baseUrl}PlantsImages/${interest.plant_icon}")
                .into(binding.tagImage)

            binding.root.setOnClickListener {
                selectedInterest = interest
                notifyDataSetChanged()
                func.invoke(selectedInterest)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmerInterestHolder {
        return FarmerInterestHolder(SingleInterestLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: FarmerInterestHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
}