package com.afrosoft.csadatacenter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.ActivityInterestsBinding
import com.afrosoft.csadatacenter.databinding.DialogFilterInterestBinding
import com.afrosoft.csadatacenter.databinding.SingleInterestBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InterestsActivity : AppCompatActivity() {

    private lateinit var adapter: InterestAdapter
    private lateinit var binding: ActivityInterestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInterestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterInterests()

        adapter = InterestAdapter(this, mutableListOf())
        binding.rvInterests.adapter = adapter

    }

    private fun filterInterests() {
        val bindingDialog = DialogFilterInterestBinding.inflate(layoutInflater,null,false)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(bindingDialog.root)
            .setCancelable(false)
            .create()

        bindingDialog.cardCrop.setOnClickListener {
            adapter.changeList(mutableListOf(Interest("Beans"),Interest("Rice"),Interest("Maize")))
            dialog.dismiss()
        }
        bindingDialog.cardLivestock.setOnClickListener {
            adapter.changeList(mutableListOf(Interest("Cattle"),Interest("Pigs"),Interest("Goats")))
            dialog.dismiss()
        }
        bindingDialog.buttonCancel.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }
}

class Interest(val name:String )

class InterestAdapter(val context: Context, var list: MutableList<Interest>)
    : RecyclerView.Adapter<InterestAdapter.InterestHolder>() {

    inner class InterestHolder(val binding : SingleInterestBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: Interest) {
            binding.txtInterest.text = obj.name
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestHolder {
        return InterestHolder(SingleInterestBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: InterestHolder, position: Int) {
        holder.displayViews(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun changeList(list: MutableList<Interest>) {
        this.list = list
        notifyDataSetChanged()
    }
}