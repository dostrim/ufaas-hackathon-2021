package com.bebridge.android_template

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bebridge.android_template.databinding.ActivityMainBinding
import com.bebridge.android_template.util.DebugManager
import com.bebridge.android_template.util.PreferenceController
import com.bebridge.android_template.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.OnSuccessListener
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var viewModel: MainViewModel

  lateinit var appUpdateManager: AppUpdateManager

  lateinit var installStateUpdatedListener: InstallStateUpdatedListener

  private val binding by lazy {
    DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    (application as CustomApplication).getComponent().inject(this)

    binding.viewModel = viewModel


    findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
      when (destination.id) {
        R.id.homeFragment -> viewModel.hideBottomNav()
        R.id.myPageFragment -> viewModel.hideBottomNav()
        R.id.viewPagerFragment -> viewModel.hideBottomNav()
        else -> viewModel.hideBottomNav()
      }
    }

    binding.bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment))

    updateChecker()
  }

  @SuppressLint("WrongConstant")
  private fun updateChecker() {
    appUpdateManager = AppUpdateManagerFactory.create(this)

    installStateUpdatedListener = InstallStateUpdatedListener { installState ->
      when (installState.installStatus()) {
        InstallStatus.DOWNLOADED -> {
          updaterDownloadCompleted()
        }
        InstallStatus.INSTALLED -> {
          appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
      }
    }
    appUpdateManager.registerListener(installStateUpdatedListener)

    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    appUpdateInfoTask.addOnSuccessListener(OnSuccessListener { appUpdateInfo ->
      when (appUpdateInfo.updateAvailability()) {
        UpdateAvailability.UPDATE_AVAILABLE -> {
          val availableVersionCode = appUpdateInfo.availableVersionCode()

          var updateTypes = -1
          if (availableVersionCode % 100 == 0) {
            updateTypes = AppUpdateType.IMMEDIATE
          } else if (availableVersionCode != PreferenceController.instance.getAvailableVersionCode()) {
            updateTypes = AppUpdateType.FLEXIBLE
          }
          PreferenceController.instance.setAvailableVersionCode(availableVersionCode)

          if (updateTypes != -1) {
            if (appUpdateInfo.isUpdateTypeAllowed(updateTypes)) {
              appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                updateTypes,
                this,
                REQUEST_UPDATE_CODE
              )
            }
          }
        }
      }
    })
  }

  override fun onResume() {
    super.onResume()

    viewModel.refreshToken()

    appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
      if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED)
          updaterDownloadCompleted()
      } else {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
          appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.IMMEDIATE,
            this,
            REQUEST_UPDATE_CODE
          )
        }
      }
    }
  }

  override fun onDestroy() {
    appUpdateManager.unregisterListener(installStateUpdatedListener)
    super.onDestroy()
  }

  private fun updaterDownloadCompleted() {
    Snackbar.make(
      findViewById(R.id.nav_host_fragment),
      "An update has just been downloaded.",
      Snackbar.LENGTH_INDEFINITE
    ).apply {
      setAction("RESTART") { appUpdateManager.completeUpdate() }
      show()
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    if (requestCode == REQUEST_UPDATE_CODE) {
      if (resultCode != RESULT_OK) {
        DebugManager.instance.log(this, "Update flow failed! Result code: $resultCode")
      }
    }
    super.onActivityResult(requestCode, resultCode, data)
    supportFragmentManager.fragments.forEach {
      it.onActivityResult(requestCode, resultCode, data)
    }
  }

  companion object {
    const val REQUEST_UPDATE_CODE = 1000
  }
}
