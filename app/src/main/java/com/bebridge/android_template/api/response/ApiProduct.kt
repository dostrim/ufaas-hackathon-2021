package com.bebridge.android_template.api.response


import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiProduct(

  @Json(name = "id")
  val productId: Long,

  val name: String?,

  val photo1: String?,

  val photo2: String?,

  @Json(name = "product_category_id")
  var categoryId: Long,

  @Json(name = "product_category")
  var categoryName: String,

  @Json(name = "product_tags")
  var productTags: List<ApiProductTag>? = null
) {


  @JsonSerializable
  data class ApiProducts(
    @Json(name = "products")
    val apiProducts: List<ApiProduct>,

    @Json(name = "page_info")
    val pageInfo: PageInfo?
  )

}
