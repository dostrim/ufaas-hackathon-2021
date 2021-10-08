package com.bebridge.android_template.viewModel

import android.content.Context
import android.net.Uri
import android.text.Editable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.bebridge.android_template.api.subscribeWhileHandlingRetrofitError
import com.bebridge.android_template.db.entity.GenderEntity
import com.bebridge.android_template.db.entity.UserEntity
import com.bebridge.android_template.fragment.EditProfileNavigator
import com.bebridge.android_template.repository.UserRepository
import java.io.File
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val user = ObservableField<UserEntity>()

    var genders: List<GenderEntity> = GenderEntity.getGenderList()

    val gender = ObservableField<GenderEntity>()

    val genderText = ObservableField<String>()

    val userNameValidationResult = ObservableInt(VALIDATION_NOT_YET)

    val genderValidationResult = ObservableInt(VALIDATION_OK)

    val birthdayValidationResult = ObservableInt(VALIDATION_OK)

    var navigator: EditProfileNavigator? = null

    val loading = ObservableBoolean(false)


    fun onCreateView(navigator: EditProfileNavigator) {
        this.navigator = navigator
        initialize()
    }

    fun initialize() {
        loading.set(true)

        userRepository.fetchUserProfile()
            .doFinally { loading.set(false) }
            .subscribeWhileHandlingRetrofitError(onSuccess = {
                this.user.set(it)
                navigator?.onLoadUser(it)
            }, onError = {
                navigator?.onError(it)
            })
    }

    fun birthdayEditTextClicked(view: View) {
        val birthday = user.get()?.birthday?.split("/")?.map { it.toIntOrNull() }
        navigator?.onBirthdayEditTextClicked(
            birthday?.getOrNull(0),
            birthday?.getOrNull(1),
            birthday?.getOrNull(2)
        )

    }

    fun setBirthday(year: Int, month: Int, dayOfMonth: Int) {
        user.get()?.run {
            birthday = "$year/$month/$dayOfMonth"
        }
        user.notifyChange()
    }

    fun setPhoto(uri: Uri) {
        user.get()?.run {
            photo = uri.toString()
        }
        user.notifyChange()
    }

    fun genderEditTextClicked(view: View) {
        navigator?.onGenderEditTextClicked()
    }

    fun genderSelected(position: Int, context: Context) {
        val selectedGender = genders.getOrNull(position)
        gender.set(selectedGender)
        genderText.set(selectedGender?.getNameString(context))
    }

    fun saveButtonClicked(view: View) {
        user.get()?.let {
            var photo: File? = null
            it.photo?.let {
                if (it.startsWith("file:")) {
                    photo = File(it.substringAfter("file:"))
                }
            }

            userRepository.putUserProfile(
                it.userName,
                gender.get()?.code,
                it.birthday,
                photo
            ).subscribeWhileHandlingRetrofitError(
                onSuccess = {
                    userRepository.getUserProfile()
                        .doFinally { loading.set(false) }
                        .subscribeWhileHandlingRetrofitError(
                            onSuccess = {
                            },
                            onError = {
                                navigator?.onError(it)
                            }
                        )
                    navigator?.onSuccessEditProfile()
                },
                onError = {
                    navigator?.onError(it)
                    loading.set(false)
                }
            )
        }
    }


    fun userNameTextChanged(s: Editable) {
        if (s.trim().toString().isEmpty()) {
            userNameValidationResult.set(VALIDATION_ERROR)
        } else {
            userNameValidationResult.set(VALIDATION_OK)
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
