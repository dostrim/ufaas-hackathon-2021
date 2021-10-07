package com.afrosoft.csadatacenter.models

data class UserResponse(
    val status_code: Int,
    val status_message: String,
    val user: User?
)