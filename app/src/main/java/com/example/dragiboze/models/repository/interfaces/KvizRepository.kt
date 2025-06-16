package com.example.dragiboze.models.repository.interfaces

import com.example.dragiboze.models.data.Kviz

interface KvizRepository {

    suspend fun pitanja(): List<Kviz.Pitanje>

    suspend fun objaviRez(rezultat: Double): Double

}