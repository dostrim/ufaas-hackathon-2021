package com.afrosoft.csadatacenter.models

class Interest(
    val id: String?,
    val plant_name: String?,
    val plant_image: String?,
    val plant_icon: String?
) {

    override fun equals(other: Any?): Boolean {
        if (other is Interest) {
            return other.id == id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}