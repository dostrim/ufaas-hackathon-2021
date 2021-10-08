package com.bebridge.android_template.viewModel

import androidx.databinding.ObservableBoolean
import com.bebridge.android_template.fragment.MenuNavigator
import com.bebridge.android_template.util.PreferenceController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuViewModel @Inject constructor() {

  var navigator: MenuNavigator? = null

  private val pc = PreferenceController.instance

  val loggedIn = ObservableBoolean(false)

  fun onCreateView(navigator: MenuNavigator?) {

    this.navigator = navigator

    loggedIn.set(pc.getAccessToken() != null)
  }

  fun onDestroy() {
    navigator = null
  }
}