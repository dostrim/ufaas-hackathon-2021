package com.bebridge.android_template.viewModel

import android.text.Editable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.bebridge.android_template.fragment.SignUpNavigator
import com.bebridge.android_template.repository.UserRepository
import com.bebridge.android_template.util.PreferenceController
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  var navigator: SignUpNavigator? = null

  val loading = ObservableBoolean(false)

  val userName = ObservableField<String>()

  val email = ObservableField<String>()

  private val emailValidationResult = ObservableInt(VALIDATION_NOT_YET)

  val userNameValidationResult = ObservableInt(VALIDATION_NOT_YET)

  fun onCreateView(navigator: SignUpNavigator) {
    this.navigator = navigator
  }

  fun signUpButtonClicked(view: View) {
    //save details to server
    PreferenceController.instance.setIsUserDataDone(true)
    navigator?.onSuccessSignup()
  }

  fun userNameTextChanged(s: Editable) {
    if (s.trim().toString().isEmpty()) {
      userNameValidationResult.set(VALIDATION_ERROR)
    } else {
      userNameValidationResult.set(VALIDATION_OK)
    }
  }

  fun emailTextChanged(s: Editable) {
    if (!(s.trim().toString().contains("@") && s.toString().contains("."))) {
      emailValidationResult.set(VALIDATION_ERROR)
    } else {
      emailValidationResult.set(VALIDATION_OK)
    }

  }

  fun onDestroy() {
    navigator = null
  }

  companion object {
    const val VALIDATION_NOT_YET = 0
    const val VALIDATION_ERROR = 1
    const val VALIDATION_OK = 2
  }
}
