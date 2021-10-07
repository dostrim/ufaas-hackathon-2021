package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.SingleAttackLayoutBinding
import com.afrosoft.csadatacenter.models.Attack
import com.afrosoft.csadatacenter.network.NetworkClient
import com.bumptech.glide.Glide
import android.content.Intent




class AttacksAdapter(val context: Context, var list: MutableList<Attack>): RecyclerView.Adapter<AttacksAdapter.AttackHolder>() {

    inner class AttackHolder(val binding: SingleAttackLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(attack: Attack) {
            binding.name.text = attack.name
            Glide.with(context).asBitmap().load("${NetworkClient().baseUrl}PlantDiseaseImages/${attack.image}")
                .into(binding.image)

            binding.root.setOnClickListener{
                val intent = Intent(context, PlantDiseasePreviewActivity::class.java)
                intent.putExtra("image", attack.image)
                intent.putExtra("name", attack.name)
                intent.putExtra("description", attack.description)
                intent.putExtra("cause", attack.cause)
                intent.putExtra("symptom", attack.symptom)
                intent.putExtra("treatment", attack.treatment)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttackHolder {
        return AttackHolder(SingleAttackLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: AttackHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
    fun changeData(diseases: MutableList<Attack>?) {
        this.list = diseases!!
        notifyDataSetChanged()
    }
}