package com.bebridge.android_template.api.response

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ApiToken(
    val token: String,
    val expire: String,
    val user: LoginUser
)

@JsonSerializable
data class LoginUser (
    @Json(name = "password_reset")
    val passwordReset: Boolean,

    val created: Boolean,

    val confirmed: Boolean

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (passwordReset) 1 else 0)
        parcel.writeByte(if (created) 1 else 0)
        parcel.writeByte(if (confirmed) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<LoginUser> {
        override fun createFromParcel(parcel: Parcel): LoginUser {
            return LoginUser(parcel)
        }

        override fun newArray(size: Int): Array<LoginUser?> {
            return arrayOfNulls(size)
        }
    }
}