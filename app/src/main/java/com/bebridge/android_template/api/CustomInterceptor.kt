package com.bebridge.android_template.api

import android.content.Context
import com.bebridge.android_template.BuildConfig
import com.bebridge.android_template.util.PreferenceController
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(private val context: Context) : Interceptor {

  private val pc = PreferenceController.instance

  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()
    val request = original.newBuilder()
      .header("X-Api-Key", BuildConfig.API_KEY)
      .method(original.method(), original.body())

    pc.getAccessToken()?.let {
        request.addHeader("Authorization", "Bearer $it")
      }

    var urlBuilder = original.url()
      .newBuilder()


    request.url(urlBuilder.toString())

    return chain.proceed(request.build())
  }
}