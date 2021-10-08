package com.bebridge.android_template.api.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiProductTag(

  @Json(name = "id")
  val productTagId: Long,

  val name: String?
)