package com.bebridge.android_template.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "product_product_tag", primaryKeys = ["productId", "productTagId"])
data class ProductProductTagEntity(
  @ColumnInfo(index = true)
  val productId: Long,

  @ColumnInfo(index = true)
  val productTagId: Long
)