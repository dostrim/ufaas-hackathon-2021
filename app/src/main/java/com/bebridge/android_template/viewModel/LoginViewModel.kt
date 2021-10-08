package com.bebridge.android_template.viewModel

import android.text.Editable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.bebridge.android_template.api.subscribeWhileHandlingRetrofitError
import com.bebridge.android_template.fragment.LoginNavigator
import com.bebridge.android_template.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val email = ObservableField<String>()

    val password = ObservableField<String>()

    val phoneNumberValidationResult = ObservableInt(VALIDATION_NOT_YET)

    val loading = ObservableBoolean(false)

    private var navigator: LoginNavigator? = null

    fun onCreateView(
        navigator: LoginNavigator
    ) {
        this.navigator = navigator
        loading.set(false)
    }

    fun onDestroyView() {
        navigator = null
    }

    fun logInButtonClicked(view: View) {
        loading.set(true)
        //Request for otp
        navigator?.onSuccessLogin()
    }

    fun phoneNumberTextChanged(s: Editable) {
        if (checkLoginIdValidation(s)) {
            phoneNumberValidationResult.set(VALIDATION_OK)
        } else {
            phoneNumberValidationResult.set(VALIDATION_ERROR)
        }
    }

    private fun checkLoginIdValidation(s: Editable): Boolean {
        return s.toString().length == 9
    }

    private fun checkEmailValidation(s: Editable): Boolean {
        return s.toString().contains("@") && s.toString().contains(".")
    }

    companion object {
        const val VALIDATION_NOT_YET = 0
        const val VALIDATION_ERROR = 1
        const val VALIDATION_OK = 2
    }
}