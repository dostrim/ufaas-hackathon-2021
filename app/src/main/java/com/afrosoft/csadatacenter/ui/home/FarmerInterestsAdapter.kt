package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.SingleInterestLayoutBinding
import com.afrosoft.csadatacenter.models.FarmerInterest

class FarmerInterestsAdapter(val context: Context, val list: MutableList<FarmerInterest>): RecyclerView.Adapter<FarmerInterestsAdapter.FarmerInterestHolder>() {

    inner class FarmerInterestHolder(val binding: SingleInterestLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(interest: FarmerInterest) {
            binding.tagName.text = interest.name
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