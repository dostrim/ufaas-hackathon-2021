package com.bebridge.android_template.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bebridge.android_template.db.entity.ProductCategoryEntity
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.entity.ProductProductTagEntity
import com.bebridge.android_template.db.entity.ProductTagEntity

data class ProductWithCategoryAndTags(

  @Embedded
  val product: ProductEntity,

  @Relation(
    parentColumn = "productId",
    entityColumn = "productTagId",
    associateBy = Junction(ProductProductTagEntity::class)
  )
  val productTagEntities: List<ProductTagEntity>,

  @Embedded(prefix = "product_category")
  val productCategoryEntity: ProductCategoryEntity

)