package com.bebridge.android_template.api.response

import android.content.Context
import com.bebridge.android_template.R
import com.crashlytics.android.Crashlytics
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

data class ErrorResponse(

  val status: String?,

  val messages: List<String>?,

  val stringResourceId: Int?

) {

  fun messageString(context: Context): String {
    var string = ""
    var i = 0
    messages?.forEach {
      if (i > 0) {
        string += ", "
      }
      string += it
      i++
    }

    stringResourceId?.let {
      string = context.getString(it)
    }

    return string
  }

  companion object {
    private val jsonAdapter: JsonAdapter<ErrorResponse> = Moshi.Builder()
      .build()
      .adapter(ErrorResponse::class.java)

    @JvmStatic
    fun parseJson(json: String?): ErrorResponse? {
      if (json == null) return null
      return jsonAdapter.fromJson(json)
    }

    @JvmStatic
    fun parseThrowable(e: Throwable): ErrorResponse {

      return when (e) {

        is HttpException -> {
          parseJson(e.response()?.errorBody()?.string()) ?: throw e
        }

        is UnknownHostException -> {
          ErrorResponse("UnknownHostException", null, R.string.error_response_no_internet)
        }

        is SocketTimeoutException -> {
          ErrorResponse("SocketTimeOutException", null, R.string.error_response_no_internet)
        }

        is SSLException -> {
          ErrorResponse("SSLException", null, R.string.error_response_no_internet)
        }

        else -> {
          e.printStackTrace()
          Crashlytics.logException(e)
          ErrorResponse("UnKnownException", listOf(e.toString()), null)
        }
      }
    }
  }

}