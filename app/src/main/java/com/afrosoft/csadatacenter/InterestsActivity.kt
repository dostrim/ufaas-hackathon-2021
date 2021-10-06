package com.afrosoft.csadatacenter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.ActivityInterestsBinding
import com.afrosoft.csadatacenter.databinding.DialogFilterInterestBinding
import com.afrosoft.csadatacenter.databinding.SingleInterestBinding
import com.afrosoft.csadatacenter.models.Interest
import com.afrosoft.csadatacenter.network.NetworkClient
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InterestsActivity : AppCompatActivity() {

    private lateinit var appPreferences: AppPreferences
    private lateinit var adapter: InterestAdapter
    private lateinit var binding: ActivityInterestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appPreferences = AppPreferences(this)

        binding = ActivityInterestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterInterests()

        adapter = InterestAdapter(this, mutableListOf()){
            binding.buttonContinue.visibility =  if (adapter.selectedInterests.isEmpty()){
                View.GONE
            }else{
                View.VISIBLE
            }
        }
        binding.rvInterests.adapter = adapter

        binding.buttonContinue.setOnClickListener {
            if (adapter.selectedInterests.isEmpty()){
                Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }else{
                appPreferences.saveFarmerInterests(adapter.selectedInterests)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun filterInterests() {
        val bindingDialog = DialogFilterInterestBinding.inflate(layoutInflater,null,false)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(bindingDialog.root)
            .setCancelable(false)
            .create()

        bindingDialog.cardCrop.setOnClickListener {
            adapter.changeList(appPreferences.getAllPlantData())
            dialog.dismiss()
        }

        bindingDialog.cardLivestock.setOnClickListener {
            adapter.changeList(mutableListOf())
            dialog.dismiss()
        }
        bindingDialog.buttonCancel.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }
}

class InterestAdapter(val context: Context, var list: MutableList<Interest>, val func:()->Unit)
    : RecyclerView.Adapter<InterestAdapter.InterestHolder>() {

    var selectedInterests = mutableListOf<Interest>()

    inner class InterestHolder(val binding : SingleInterestBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun displayViews(obj: Interest) {

            binding.txtInterest.text = obj.plant_name

            binding.imgSelected.visibility =  if (selectedInterests.contains(obj)){
                View.VISIBLE
            }else{
                View.GONE
            }

            Glide.with(context).asBitmap().load("${NetworkClient().baseUrl}PlantImages/${obj.plant_icon}")
                .into(binding.imgInterest)

            binding.root.setOnClickListener {

                if (selectedInterests.contains(obj)){
                    selectedInterests.remove(obj)
                }else{
                    selectedInterests.add(obj)
                }

                notifyDataSetChanged()
                func.invoke()

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