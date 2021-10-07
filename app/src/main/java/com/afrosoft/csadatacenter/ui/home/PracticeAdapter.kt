package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.SinglePractiseLayoutBinding
import com.afrosoft.csadatacenter.models.Practice

class PracticeAdapter(val context: Context, val list: MutableList<Practice>): RecyclerView.Adapter<PracticeAdapter.PracticeHolder>() {

    inner class PracticeHolder(val binding: SinglePractiseLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(practice: Practice) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeHolder {
        return PracticeHolder(SinglePractiseLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: PracticeHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
}