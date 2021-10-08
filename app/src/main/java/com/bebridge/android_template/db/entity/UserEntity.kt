package com.bebridge.android_template.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity(tableName = "user")
@JsonSerializable
data class UserEntity constructor(

    @PrimaryKey
    @Json(name = "id")
    val userId: String,

    @Json(name = "user_name")
    var userName: String?,

    var email: String?,

    var birthday: String?,

    @Json(name = "gender_code")
    var genderCode: String?,

    var gender: String?,

    var photo: String?,

    @Json(name = "created_at")
    val createdAt: String?,

    @Json(name = "updated_at")
    val updatedAt: String?,

    @Json(name = "deleted_at")
    val deletedAt: String?
)