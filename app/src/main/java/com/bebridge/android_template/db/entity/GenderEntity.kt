package com.bebridge.android_template.db.entity

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.bebridge.android_template.R
import se.ansman.kotshi.JsonSerializable

@Entity(tableName = "gender")
@JsonSerializable
data class GenderEntity constructor(
    @ColumnInfo
    var id: Long = 0,
    var stringResourceId: Int,
    var code: String
) {

    fun getNameString(context: Context) = context.getString(stringResourceId)

    companion object {
        fun getGenderList(): List<GenderEntity> = listOf(
            GenderEntity(1, R.string.gender_male, "male"),
            GenderEntity(2, R.string.gender_female, "female"),
            GenderEntity(3, R.string.gender_other, "other")
        )

        fun codeOf(code: String?): GenderEntity? {
            getGenderList().forEach {
                if (code == it.code) {
                    return it
                }
            }
            return null
        }
    }
}