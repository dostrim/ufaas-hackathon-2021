package com.afrosoft.csadatacenter.models

data class MarketPrice(
    val created_at: String,
    val id: String,
    val market: Market,
    val market_id: String,
    val plant_id: String,
    val price: String,
    val status: String,
    val units: String
)