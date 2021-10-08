package com.bebridge.android_template.viewModel

import android.text.Editable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.bebridge.android_template.fragment.ConfirmNumberFragment
import com.bebridge.android_template.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfirmNumberViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  var navigator: ConfirmNumberFragment? = null

  val loading = ObservableBoolean(false)

  val email = ObservableField<String>()

  val success = ObservableBoolean(false)

  val codeValidationResult = ObservableInt(VALIDATION_NOT_YET)

  fun onCreateView(navigator: ConfirmNumberFragment) {
    this.navigator = navigator
  }

  fun confirmNumberButtonClicked(view: View) {
    navigator?.onSuccessConfirmPassword()
  }

  fun codeTextChanged(s: Editable) {
    if (checkCodeValidation(s)) {
      codeValidationResult.set(LoginViewModel.VALIDATION_OK)
    } else {
      codeValidationResult.set(LoginViewModel.VALIDATION_ERROR)
    }
  }

  private fun checkCodeValidation(s: Editable): Boolean {
    return s.toString().length == 6
  }

  fun onDestroyView() {
    navigator = null
  }

  companion object {
    const val VALIDATION_NOT_YET = 0
    const val VALIDATION_ERROR = 1
    const val VALIDATION_OK = 2
  }
}
