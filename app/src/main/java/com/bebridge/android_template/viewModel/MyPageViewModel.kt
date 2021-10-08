package com.bebridge.android_template.viewModel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.bebridge.android_template.api.subscribeWhileHandlingRetrofitError
import com.bebridge.android_template.db.entity.UserEntity
import com.bebridge.android_template.fragment.MyPageNavigator
import com.bebridge.android_template.repository.UserRepository
import com.bebridge.android_template.util.PreferenceController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPageViewModel @Inject constructor(private val userRepository: UserRepository) {

  val user = ObservableField<UserEntity>()

  var navigator: MyPageNavigator? = null

  val loading = ObservableBoolean(false)

  val isLoggedIn: ObservableBoolean = ObservableBoolean(false)

  fun onCreateView(navigator: MyPageNavigator) {
    this.navigator = navigator

    isLoggedIn.set(PreferenceController.instance.getAccessToken() != null)

    getUserData()
  }

  fun getUserData() {
    if (isLoggedIn.get()) {
      if (!loading.get()) {
        loading.set(true)
        userRepository.getUserProfile()
          .doFinally { loading.set(false) }
          .subscribeWhileHandlingRetrofitError(
            {
              user.set(it)
            },
            {
              navigator?.onError(it)
            }
          )
      }
    }
  }

  fun onDestroyView() {
    navigator = null
  }

}