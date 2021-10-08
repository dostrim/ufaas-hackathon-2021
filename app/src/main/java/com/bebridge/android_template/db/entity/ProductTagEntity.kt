package com.bebridge.android_template.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.bebridge.android_template.db.entity.ProductEntity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity(tableName = "product_tag")
data class ProductTagEntity(
  @PrimaryKey
  val productTagId: Long,

  val name: String?
)



