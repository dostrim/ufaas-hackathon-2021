package com.bebridge.android_template.util

import android.content.Context
import android.content.SharedPreferences
import org.threeten.bp.ZonedDateTime

class PreferenceController private constructor() {

  private var preferences: SharedPreferences? = null

  fun setIsOnBoardingDone(value: Boolean) {
    preferences?.edit()?.apply {
      putBoolean(FLAG_IS_ON_BOARDING_DONE, value)
      apply()
    }
  }

  fun getIsOnBoardingDone(): Boolean {
    return preferences?.getBoolean(FLAG_IS_ON_BOARDING_DONE, false) ?: false
  }

  fun setIsUserDataDone(value: Boolean) {
    preferences?.edit()?.apply {
      putBoolean(FLAG_IS_USER_DATA_DONE, value)
      apply()
    }
  }

  fun getIsUserDataDone(): Boolean {
    return preferences?.getBoolean(FLAG_IS_USER_DATA_DONE, false) ?: false
  }

  fun setLoginUserId(id: String?) {
    preferences?.edit()?.apply {
      putString(FLAG_LOGIN_USER_ID, id)
      apply()
    }
  }

  fun getLoginUserId(): String? {
    return preferences?.getString(FLAG_LOGIN_USER_ID, null)
  }

  fun setAccessToken(accessToken: String?) {
    preferences?.edit()
      ?.apply {
        putString(FLAG_ACCESS_TOKEN, accessToken)
        apply()
      }
  }

  fun getAccessToken(): String? {
    return preferences?.getString(FLAG_ACCESS_TOKEN, null)
  }

  fun initialize(context: Context) {
    preferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE)
  }

  fun logout() {
    setAccessToken(null)
    setEmail(null)
    setPassword(null)
    setLoginType(LOGIN_TYPE_NOT_YET)
    setLastLoggedInAt(null)
  }

  fun setLastLoggedInAt(value: ZonedDateTime?) {
    preferences?.edit()?.apply {
      putString(FLAG_LAST_LOGGED_IN_AT, value?.toString())
      apply()
    }
  }

  fun getLastLoggedInAt(): ZonedDateTime? {
    return preferences?.getString(FLAG_LAST_LOGGED_IN_AT, null)?.let {
      ZonedDateTime.parse(it)
    }
  }

  fun setLoginType(type: Int) {
    preferences?.edit()?.apply {
      putInt(FLAG_LOGIN_TYPE, type)
      apply()
    }
  }

  fun getLoginType(): Int {
    return preferences?.getInt(FLAG_LOGIN_TYPE, LOGIN_TYPE_NOT_YET) ?: LOGIN_TYPE_NOT_YET
  }

  fun setEmail(email: String?) {
    preferences?.edit()?.apply {
      putString(FLAG_EMAIL, email)
      apply()
    }
  }

  fun getEmail(): String? {
    return preferences?.getString(FLAG_EMAIL, null)
  }

  fun setPassword(password: String?) {
    preferences?.edit()?.apply {
      putString(FLAG_PASSWORD, password?.let { CryptoUtil.encrypto(password, SECRET_KEY) })
      apply()
    }
  }

  fun getPassword(): String? {
    return preferences?.getString(FLAG_PASSWORD, null)?.let { CryptoUtil.decrypto(it, SECRET_KEY) }
  }

  fun setAvailableVersionCode(availableVersionCode: Int) {
    preferences?.edit()
      ?.apply {
        putInt(FLAG_AVAILABLE_VERSION_CODE, availableVersionCode)
        apply()
      }
  }

  fun getAvailableVersionCode(): Int {
    return preferences?.getInt(FLAG_AVAILABLE_VERSION_CODE, 0) ?: 0
  }

  fun getLanguageCode(): String? {
    return preferences?.getString(FLAG_LANGUAGE_CODE, null)
  }

  fun getCurrencyCode(): String? {
    return preferences?.getString(FLAG_CURRENCY_CODE, null)
  }

  companion object {

    @JvmStatic
    val instance: PreferenceController = PreferenceController()

    const val FLAG_IS_ON_BOARDING_DONE = "flag_is_on_boarding_done"

    const val FLAG_LOGIN_USER_ID = "flag_login_user_id"

    const val FLAG_ACCESS_TOKEN = "flag_access_token"

    const val FLAG_LAST_LOGGED_IN_AT = "flag_last_logged_in_at"

    const val FLAG_LOGIN_TYPE = "flag_login_type"

    const val FLAG_EMAIL = "flag_email"

    const val FLAG_PASSWORD = "flag_password"

    const val FLAG_AVAILABLE_VERSION_CODE = "available_version_code"

    const val FLAG_LANGUAGE_CODE = "flag_language_code"

    const val FLAG_CURRENCY_CODE = "flag_currency_code"

    const val FLAG_IS_USER_DATA_DONE = "flag_is_user_data_done"

    const val LOGIN_TYPE_NOT_YET = 0

    const val LOGIN_TYPE_EMAIL = 1

    const val LOGIN_TYPE_FACEBOOK = 2

    const val LOGIN_TYPE_GOOGLE = 3

    const val LOGIN_TYPE_LINE = 4

    const val LOGIN_TYPE_APPLE = 5

    const val SECRET_KEY = "vyzrvmwwrcp3p1p8yo"

  }

}