package com.afrosoft.csadatacenter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.afrosoft.csadatacenter.tasks.PlantsDataFetcher
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.afrosoft.csadatacenter.ui.access.LoginActivity
import com.google.android.gms.security.ProviderInstaller
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val prefs by lazy {
        AppPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT

            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_splash)

        /*required to support android apis 4.4 and below*/
        ProviderInstaller.installIfNeeded(applicationContext)


        Timer("GoToWelcomeScreen", false).schedule(3000) {
            if (prefs.isFirstTime()){
                startActivity(Intent(this@SplashActivity, LanguagesActivity::class.java))
                finish()
            }else{
                if(AppPreferences(this@SplashActivity).getUser()==null)
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                else
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                finish()
            }

        }

        /*background task to provide plant data*/
        WorkManager.getInstance(this)
            .enqueue(OneTimeWorkRequestBuilder<PlantsDataFetcher>().build())

    }
}