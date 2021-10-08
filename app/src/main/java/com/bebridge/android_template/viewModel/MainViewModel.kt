package com.bebridge.android_template.viewModel

import androidx.databinding.ObservableBoolean
import com.bebridge.android_template.repository.ProductRepository
import com.bebridge.android_template.repository.RefreshTokenRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
  private val productRepository: ProductRepository,
  private val refreshTokenRepository: RefreshTokenRepository
) {

  val showBottomNav = ObservableBoolean(true)

  fun showBottomNav() {
    showBottomNav.set(true)
  }

  fun hideBottomNav() {
    showBottomNav.set(false)
  }

  fun refreshToken() {
    refreshTokenRepository.refreshTokenIfNeeded()
      .subscribeOn(Schedulers.computation())
      .subscribe()
  }
}