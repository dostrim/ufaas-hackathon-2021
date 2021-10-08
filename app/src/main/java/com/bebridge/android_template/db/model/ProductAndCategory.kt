package com.bebridge.android_template.db.model

import androidx.room.Embedded
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.entity.ProductCategoryEntity

data class ProductAndCategory(
  @Embedded(prefix = "product_category")
  val productCategoryEntity: ProductCategoryEntity,

  @Embedded(prefix = "product")
  val product: ProductEntity
)