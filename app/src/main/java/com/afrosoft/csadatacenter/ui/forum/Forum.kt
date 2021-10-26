package com.afrosoft.csadatacenter.ui.forum

data class Forum(
    val content: String,
    val created_at: String,
    val down_vote: String,
    val farmer: Farmer,
    val farmer_id: String,
    val id: String,
    val picture: String,
    val plant_id: String,
    val title: String,
    val up_vote: String
)