package com.bebridge.android_template.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

class DebugManager {

  private var isDebug: Boolean = false

  fun isDebug() = isDebug

  fun initialize(context: Context) {
    isDebug = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
  }

  fun log(
    currentClass: Any,
    message: String
  ) {
    if (isDebug) {
      Log.d("###", currentClass.javaClass.canonicalName + ": $message")
    }
  }

  companion object {
    val instance = DebugManager()
  }

}