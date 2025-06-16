package com.example.dragiboze.models.api.interfaces

import com.example.dragiboze.models.api.models.TakmicarApiModel
import com.example.dragiboze.models.api.models.rezultatReq
import com.example.dragiboze.models.api.models.rezultatRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TakmicarApi {

    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("category") categoryId: Int,
    ): List<TakmicarApiModel>

    @POST("leaderboard")
    suspend fun postLeaderboard(
        @Body leaderboard: rezultatReq
    ): rezultatRes
}