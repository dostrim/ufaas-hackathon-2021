package com.afrosoft.csadatacenter.models

data class Specialist(
    val created_at: String,
    val description: String,
    val distance: Double,
    val id: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val name: String,
    val speciality: String,
    val type: String
)