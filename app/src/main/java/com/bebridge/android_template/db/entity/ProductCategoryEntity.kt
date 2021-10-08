package com.bebridge.android_template.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "product_category")
data class ProductCategoryEntity(
  @PrimaryKey
  @Json(name = "id")
  var categoryId: Long,

  var name: String
)
