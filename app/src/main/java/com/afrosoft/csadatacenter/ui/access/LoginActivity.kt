package com.afrosoft.csadatacenter.ui.access

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.afrosoft.csadatacenter.MainActivity
import com.afrosoft.csadatacenter.databinding.ActivityLoginBinding
import com.afrosoft.csadatacenter.models.UserResponse
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var appPreferences: AppPreferences
    lateinit var binding: ActivityLoginBinding

    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.ccp.registerCarrierNumberEditText(binding.phoneNumber)
        binding.loginBtn.setOnClickListener {
            
        AndroidNetworking.post("{BASE_URL}api/sign_in")
                    .addBodyParameter("phone_number",binding.ccp.fullNumberWithPlus)
                    .addBodyParameter("password",binding.password.text.toString())
                    .build()
                    .getAsString(object : StringRequestListener {
                        override fun onResponse(response: String?) {
        
                            val nrf = Gson().fromJson(response, UserResponse::class.java)
                            if (nrf.status_code == 200){
                                //action
                                appPreferences.saveUser(nrf.user)
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }

                            Toast.makeText(this@LoginActivity, nrf.status_message, Toast.LENGTH_SHORT).show()
        
                        }
        
                        override fun onError(anError: ANError?) {
                            Log.d(TAG, "onError: ${anError?.errorCode}")
                            Log.d(TAG, "onError: ${anError?.errorBody}")
                            Toast.makeText(this@LoginActivity, "No Internet", Toast.LENGTH_SHORT).show()
                        }
                    })
        
        //
        }
    }
}