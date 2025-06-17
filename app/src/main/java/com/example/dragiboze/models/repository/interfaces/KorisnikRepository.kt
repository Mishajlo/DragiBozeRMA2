package com.example.dragiboze.models.repository.interfaces

import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.datastore.UserData

interface KorisnikRepository {

    suspend fun getUserData(): UserData

    suspend fun register(userData: UserData): String

    suspend fun getAllIgre(): List<IgraDbModel>

    suspend fun getUserIgre(): List<IgraDbModel>

    suspend fun getNo1(): Pair<Int, Double>

    suspend fun isRegistered(): Boolean

}