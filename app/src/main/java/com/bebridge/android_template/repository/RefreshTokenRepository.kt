package com.bebridge.android_template.repository

import com.bebridge.android_template.api.ApiService
import com.bebridge.android_template.api.response.*
import com.bebridge.android_template.util.DebugManager
import com.bebridge.android_template.util.PreferenceController
import io.reactivex.Completable
import io.reactivex.Single
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenRepository @Inject constructor(
    private val service: ApiService.IApiService
) {
    private val pc: PreferenceController = PreferenceController.instance

    fun refreshTokenIfNeeded(): Completable {
        if (pc.getAccessToken() != null) {
            pc.getLastLoggedInAt()?.let {
                if (it < ZonedDateTime.now(ZoneId.systemDefault()).minusDays(1)) {
                    return refreshToken() ?: Completable.complete()
                }
            } ?: run {
                return refreshToken() ?: Completable.complete()
            }
        }
        return Completable.complete()
    }

    private fun refreshToken(): Completable? {
        return when (pc.getLoginType()) {

            PreferenceController.LOGIN_TYPE_EMAIL -> {
                pc.getEmail()?.let { email ->
                    pc.getPassword()?.let { password ->
                        loginByEmail(email, password).ignoreElement()
                    }
                }
            }

            PreferenceController.LOGIN_TYPE_FACEBOOK -> {
                pc.getEmail()?.let {
                    refreshTokenForFacebookLogin(it).ignoreElement()
                }
            }

            PreferenceController.LOGIN_TYPE_GOOGLE -> {
                pc.getEmail()?.let {
                    refreshTokenForGoogleLogin(it).ignoreElement()
                }
            }

            PreferenceController.LOGIN_TYPE_LINE -> {
                pc.getEmail()?.let {
                    refreshTokenForLineLogin(it).ignoreElement()
                }
            }

            PreferenceController.LOGIN_TYPE_APPLE -> {
                pc.getEmail()?.let {
                    refreshTokenForAppleLogin(it).ignoreElement()
                }
            }

            else -> null
        }
    }

    private fun loginByEmail(email: String, password: String): Single<ApiToken> =
        service
            .loginByEmail(email, password)
            .doOnSuccess {
                pc.setAccessToken(it.token)
                pc.setLoginType(PreferenceController.LOGIN_TYPE_EMAIL)
                pc.setEmail(email)
                pc.setPassword(password)
                pc.setLastLoggedInAt(ZonedDateTime.now(ZoneId.systemDefault()))
            }

    private fun refreshTokenForFacebookLogin(email: String): Single<ApiToken> = service
        .loginWithFacebook(null, email, pc.getLanguageCode(), pc.getCurrencyCode())
        .doOnSuccess {
            DebugManager.instance.log(this, "refreshTokenForFacebookLogin success")
            pc.setAccessToken(it.token)
            pc.setLoginType(PreferenceController.LOGIN_TYPE_FACEBOOK)
            pc.setLastLoggedInAt(ZonedDateTime.now(ZoneId.systemDefault()))
        }

    private fun refreshTokenForLineLogin(email: String): Single<ApiToken> = service
        .loginWithLine(null, email, pc.getLanguageCode(), pc.getCurrencyCode())
        .doOnSuccess {
            DebugManager.instance.log(this, "refreshTokenForLineLogin success")
            pc.setAccessToken(it.token)
            pc.setLoginType(PreferenceController.LOGIN_TYPE_LINE)
            pc.setLastLoggedInAt(ZonedDateTime.now(ZoneId.systemDefault()))
        }

    private fun refreshTokenForAppleLogin(email: String): Single<ApiToken> = service
        .loginWithApple(null, email, pc.getLanguageCode(), pc.getCurrencyCode(), "1")
        .doOnSuccess {
            DebugManager.instance.log(this, "refreshTokenForAppleLogin success")
            pc.setAccessToken(it.token)
            pc.setLoginType(PreferenceController.LOGIN_TYPE_APPLE)
            pc.setLastLoggedInAt(ZonedDateTime.now(ZoneId.systemDefault()))
        }

    private fun refreshTokenForGoogleLogin(email: String): Single<ApiToken> {
        return service
            .loginWithGoogle(null, email, pc.getLanguageCode(), pc.getCurrencyCode())
            .doOnSuccess {
                DebugManager.instance.log(this, "refreshTokenForGoogleLogin success")
                pc.setAccessToken(it.token)
                pc.setLoginType(PreferenceController.LOGIN_TYPE_GOOGLE)
                pc.setLastLoggedInAt(ZonedDateTime.now(ZoneId.systemDefault()))
            }
    }


    companion object {

    }
}