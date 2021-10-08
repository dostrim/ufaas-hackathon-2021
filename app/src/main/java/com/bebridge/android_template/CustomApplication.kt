package com.bebridge.android_template

import android.app.Application
import com.bebridge.android_template.di.AppComponent
import com.bebridge.android_template.di.AppModule
import com.bebridge.android_template.di.DaggerAppComponent
import com.bebridge.android_template.util.DebugManager
import com.bebridge.android_template.util.PreferenceController
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen

class CustomApplication : Application() {

  private lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()

    appComponent = DaggerAppComponent.builder()
      .appModule(AppModule(this))
      .build()

    AndroidThreeTen.init(this)

    PreferenceController.instance.initialize(this)

    DebugManager.instance.initialize(this)

    if (DebugManager.instance.isDebug()) {
      Stetho.initialize(
        Stetho.newInitializerBuilder(this)
          .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
          .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
          .build()
      )
    }
  }

  fun getComponent(): AppComponent {
    return appComponent
  }

}