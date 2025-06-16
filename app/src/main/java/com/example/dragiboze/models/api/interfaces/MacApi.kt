package com.example.dragiboze.models.api.interfaces

import com.example.dragiboze.models.api.models.MacApiModel
import com.example.dragiboze.models.api.models.MackicaSlicica
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MacApi {

    @GET("breeds")
    suspend fun getAllMace(): List<MacApiModel>

    @GET("breeds/{id}")
    suspend fun getMaca(
        @Path("id") macaId: String
    ): MacApiModel

    @GET("images/{id}")
    suspend fun getMackicaSlicica(
        @Path("id") slikaId: String
    ): MackicaSlicica

    @GET("images/search")
    suspend fun getSlikeZaMacu(
        @Query("breed_ids") breedId: String,
        @Query("limit") limit: Int = 20
    ): List<MackicaSlicica>

}