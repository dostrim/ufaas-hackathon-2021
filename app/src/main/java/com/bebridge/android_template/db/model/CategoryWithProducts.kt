package com.bebridge.android_template.db.model

import androidx.room.Embedded
import androidx.room.Relation
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.entity.ProductCategoryEntity

data class CategoryWithProducts(
  @Embedded
  val productCategoryEntity: ProductCategoryEntity,

  @Relation(
    parentColumn = "categoryId",
    entityColumn = "categoryId"
  )
  val products: List<ProductEntity>

)