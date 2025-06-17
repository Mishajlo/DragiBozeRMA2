package com.example.dragiboze.models.repository.interfaces

import com.example.dragiboze.views.kviz.KvizContract

interface KvizRepository {

    suspend fun pitanja(): List<KvizContract.Pitanje>

    suspend fun objaviRez(rezultat: Double): Double

}