package com.example.dragiboze.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "igre")
data class IgraDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val korisnik: String,
    val rezultat: Double,
    val vremeKreiranja: Long,
    var objavi: Boolean = false
)
