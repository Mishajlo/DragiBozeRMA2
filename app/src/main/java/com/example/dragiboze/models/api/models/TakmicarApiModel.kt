package com.example.dragiboze.models.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TakmicarApiModel(
    val category: Int = 0,
    val nickname: String = "",
    val result: Double = 0.0,
    val createdAt: Long = 0L
)


@Serializable
data class rezultatReq(
    val nickname: String,
    val result: Double,
    val category: Int
)

@Serializable
data class rezultatRes(
    val result: TakmicarApiModel,
    val ranking: Int
)