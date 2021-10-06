package com.afrosoft.csadatacenter.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrosoft.csadatacenter.databinding.ActivitySpecialistBinding

class SpecialistActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpecialistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivitySpecialistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
}