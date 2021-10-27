package com.afrosoft.csadatacenter.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.ActivityBuyFarmInputsBinding
import com.afrosoft.csadatacenter.databinding.RowFarmInputsBinding

class BuyFarmInputsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyFarmInputsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBuyFarmInputsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Buy Fertilizers"

        val adapter = FertilizersAdapter(this, mutableListOf(Fertilizers(),Fertilizers(),Fertilizers(),Fertilizers()))
        binding.rvFarmInputs.adapter = adapter

    }
}

class Fertilizers

class FertilizersAdapter(val context: Context, var list: MutableList<Fertilizers>)
    : RecyclerView.Adapter<FertilizersAdapter.FertilizersHolder>() {

    inner class FertilizersHolder(val binding : RowFarmInputsBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: Fertilizers) {
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FertilizersHolder {
        return FertilizersHolder(RowFarmInputsBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: FertilizersHolder, position: Int) {
        holder.displayViews(list[position])
    }

    override fun getItemCount(): Int = list.size
}