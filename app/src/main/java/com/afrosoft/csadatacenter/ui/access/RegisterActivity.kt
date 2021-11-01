package com.afrosoft.csadatacenter.ui.access

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.afrosoft.csadatacenter.InterestsActivity
import com.afrosoft.csadatacenter.MainActivity
import com.afrosoft.csadatacenter.databinding.ActivityRegisterBinding
import com.afrosoft.csadatacenter.models.UserResponse
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import dmax.dialog.SpotsDialog

class RegisterActivity : AppCompatActivity() {
    private var spotDialog: AlertDialog? = null
    private lateinit var appPreferences: AppPreferences
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidNetworking.initialize(this)

        spotDialog = SpotsDialog.Builder().setContext(this).build()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        binding.genderSpinner.setAdapter(ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,
        mutableListOf("Female","Male")))

        /*handle button clicks*/
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.ccp.registerCarrierNumberEditText(binding.phoneNumber)
        binding.registerBtn.setOnClickListener {

            if (binding.password.text.toString() != binding.password.text.toString()){
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            spotDialog?.show()
            AndroidNetworking.post("http://lyk.rkl.mybluehost.me/agro_aid/api/sign_up.php")
                        .addBodyParameter("first_name",binding.firstName.text.toString())
                        .addBodyParameter("last_name",binding.lastName.text.toString())
                        .addBodyParameter("phone_number",binding.ccp.fullNumberWithPlus)
                        .addBodyParameter("password",binding.password.text.toString())
                        .addBodyParameter("gender",binding.genderSpinner.text.toString())
                .doNotCacheResponse()
                        .build()
                        .getAsString(object : StringRequestListener {
                            override fun onResponse(response: String?) {

                                spotDialog?.dismiss()
                                val nrf = Gson().fromJson(response, UserResponse::class.java)
                                if (nrf.status_code == 200){
                                    //action
                                    appPreferences.saveUser(nrf.user)
                                    startActivity(Intent(this@RegisterActivity, InterestsActivity::class.java))
                                    finish()
                                }

                                Toast.makeText(this@RegisterActivity, nrf.status_message, Toast.LENGTH_SHORT).show()

                            }
            
                            override fun onError(anError: ANError?) {
                                Log.d("TAG-r", "onError: ${anError?.errorCode}")
                                Log.d("TAG-r", "onError: ${anError?.errorBody}")
                                Log.d("TAG-r", "onError: ${anError?.message}")
                                Log.d("TAG-r", "onError: ${anError?.errorDetail}")
                                Log.d("TAG-r", "onError: ${anError?.response}")
                                spotDialog?.dismiss()
                                Toast.makeText(this@RegisterActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
                            }
                        })
            
            
            //startActivity(Intent(this, InterestsActivity::class.java))
            //finish()
        }
    }
}