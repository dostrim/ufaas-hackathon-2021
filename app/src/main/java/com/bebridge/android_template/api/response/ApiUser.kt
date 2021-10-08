package com.bebridge.android_template.api.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiUser(

    @Json(name = "id")
    val userId: String,

    @Json(name = "user_name")
    var userName: String,

    @Json(name = "email")
    var email: String?,

    val birthday: String?,

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
) {
    @JsonSerializable
    data class Holder(val user: ApiUser)

    @JsonSerializable
    data class ApiUserData(
        @Json(name = "user")
        val apiUserData: ApiUser
    )
}