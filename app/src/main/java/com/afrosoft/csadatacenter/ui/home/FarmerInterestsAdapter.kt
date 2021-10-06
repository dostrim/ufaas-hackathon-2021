package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.Interest
import com.afrosoft.csadatacenter.R
import com.afrosoft.csadatacenter.databinding.SingleInterestLayoutBinding
import com.afrosoft.csadatacenter.models.FarmerInterest

class FarmerInterestsAdapter(val context: Context, val list: MutableList<Interest>, val func : (Interest?)->Unit): RecyclerView.Adapter<FarmerInterestsAdapter.FarmerInterestHolder>() {

    var selectedInterest : Interest? = null

    inner class FarmerInterestHolder(val binding: SingleInterestLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(interest: Interest) {
            binding.interestsBackground.setBackgroundResource(if (interest.name == selectedInterest?.name) R.drawable.black_filled_bg else R.drawable.black_border_bg )
            binding.tagName.setTextColor(if (interest.name == selectedInterest?.name) Color.WHITE else Color.BLACK )
            binding.tagName.text = interest.name

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