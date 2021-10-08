package com.bebridge.android_template.viewModel

import android.text.Editable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.bebridge.android_template.api.subscribeWhileHandlingRetrofitError
import com.bebridge.android_template.fragment.ChangePasswordFragment
import com.bebridge.android_template.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangePasswordViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    var navigator: ChangePasswordFragment? = null

    val loading = ObservableBoolean(false)

    val email = ObservableField<String>()

    val password = ObservableField<String>()

    val confirmPassword = ObservableField<String>()

    val passwordValidationResult = ObservableInt(VALIDATION_NOT_YET)

    fun onCreateView(navigator: ChangePasswordFragment) {
        this.navigator = navigator

        password.set(null)
        confirmPassword.set(null)
        passwordValidationResult.set(VALIDATION_NOT_YET)

        userRepository.fetchUserProfile()
            .subscribeWhileHandlingRetrofitError(
                {
                    email.set(it.email)
                },
                {
                    navigator.onError(it)
                }
            )
    }

    fun changePasswordButtonClicked(view: View) {
        email.get()
            ?.let { email ->
                password.get()
                    ?.let { password ->
                        userRepository.changePassword(
                            email,
                            password
                        )
                            .doFinally { loading.set(false) }
                            .subscribeWhileHandlingRetrofitError(
                                onSuccess = {
                                    navigator?.onSuccessChangePassword()
                                },
                                onError = {
                                    navigator?.onError(it)
                                }
                            )
                    }
            }
    }

    fun passwordTextChanged(s: Editable) {
        checkPasswordValidation()
    }

    private fun checkPasswordValidation() {
        if (password.get()?.isNotEmpty() == true && password.get() == confirmPassword.get()) {
            passwordValidationResult.set(VALIDATION_OK)
        } else {
            passwordValidationResult.set(VALIDATION_ERROR)
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
