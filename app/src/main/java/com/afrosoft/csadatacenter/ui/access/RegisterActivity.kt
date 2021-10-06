package com.afrosoft.csadatacenter.ui.access

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.afrosoft.csadatacenter.InterestsActivity
import com.afrosoft.csadatacenter.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genderSpinner.setAdapter(ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,
        mutableListOf("Female","Male")))

        /*handle button clicks*/
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, InterestsActivity::class.java))
            finish()
        }
    }
}