package com.bebridge.android_template.db.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity(tableName = "product")
@JsonSerializable
data class ProductEntity constructor(

  @PrimaryKey
  @Json(name = "id")
  val productId: Long,

  @ColumnInfo
  val name: String?,

  val photo1: String?,

  val photo2: String?,

  @Json(name = "product_category_id")
  val categoryId: Long?,

  val companyId: Long? = null
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readLong(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readValue(Long::class.java.classLoader) as? Long,
    parcel.readValue(Long::class.java.classLoader) as? Long
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(productId)
    parcel.writeString(name)
    parcel.writeString(photo1)
    parcel.writeString(photo2)
    parcel.writeValue(categoryId)
    parcel.writeValue(companyId)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Creator<ProductEntity> {
    override fun createFromParcel(parcel: Parcel): ProductEntity {
      return ProductEntity(parcel)
    }

    override fun newArray(size: Int): Array<ProductEntity?> {
      return arrayOfNulls(size)
    }
  }
}