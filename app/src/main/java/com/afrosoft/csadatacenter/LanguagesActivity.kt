package com.afrosoft.csadatacenter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.ActivityLanguagesBinding
import com.afrosoft.csadatacenter.databinding.SingleLanguageBinding

class LanguagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvLanguages.adapter = LanguageAdapter(this, mutableListOf(
            Language("en","English",R.drawable.flag_united_states_of_america),
            Language("lug","Luganda",R.drawable.flag_uganda),
            Language("ke","Swahili",R.drawable.flag_kenya),
            Language("fr","French",R.drawable.flag_france),
            Language("pg","Portuguese",R.drawable.flag_portugal),
        ))
    }
}

class Language(val code:String,val name:String,val flag:Int)

class LanguageAdapter(val context: AppCompatActivity, val list: MutableList<Language>)
    : RecyclerView.Adapter<LanguageAdapter.LanguageHolder>() {

    inner class LanguageHolder(val binding : SingleLanguageBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: Language) {
            binding.imgFlag.setImageResource(obj.flag)
            binding.txtFlag.text = obj.name
            binding.root.setOnClickListener {
                context.startActivity(Intent(context,InterestsActivity::class.java))
                context.finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        return LanguageHolder(SingleLanguageBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        holder.displayViews(list[position])
    }

    override fun getItemCount(): Int = list.size
}