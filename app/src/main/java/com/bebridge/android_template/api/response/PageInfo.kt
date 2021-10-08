package com.bebridge.android_template.api.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PageInfo(
  @Json(name = "total_count") val totalCount: Int?,
  @Json(name = "total_pages") val totalPages: Int?,
  @Json(name = "current_page") val currentPage: Int?,
  @Json(name = "next_page") val nextPage: Int?,
  val offset: Int?,
  val limit: Int?
) {
  companion object {
    fun createStub(): PageInfo {
      return PageInfo(null, null, null, null, null, null)
    }
  }
}