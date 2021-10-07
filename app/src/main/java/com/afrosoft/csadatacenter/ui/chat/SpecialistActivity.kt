package com.afrosoft.csadatacenter.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrosoft.csadatacenter.databinding.ActivitySpecialistBinding
import com.afrosoft.csadatacenter.models.Specialist
import com.google.gson.Gson

class SpecialistActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpecialistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivitySpecialistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val specialist = Gson().fromJson(intent.getStringExtra("specialist"),Specialist::class.java)

        binding.specialistAddress.text = specialist?.location
        binding.specialistName.text = specialist?.name
        binding.specialistDescription.text = specialist?.description
        binding.specialistSpeciality.text = specialist?.speciality
        binding.specialistDistance.text = "${specialist?.distance} Km Away"
    }
}