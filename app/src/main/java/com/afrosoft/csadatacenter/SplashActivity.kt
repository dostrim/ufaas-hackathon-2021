package com.afrosoft.csadatacenter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afrosoft.csadatacenter.ui.access.LoginActivity
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer("GoToWelcomeScreen", false).schedule(3000) {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()

        }


    }
}