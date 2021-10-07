package com.afrosoft.csadatacenter.models

data class HomeData(
    val days: Int?,
    val diseases: MutableList<Attack>?,
    val pests: MutableList<Attack>?,
    val plant_age: PlantAge?,
    val status_code: Int,
    val status_message: String
)