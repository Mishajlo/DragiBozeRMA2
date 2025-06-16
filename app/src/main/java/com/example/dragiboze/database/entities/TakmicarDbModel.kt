package com.example.dragiboze.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "takmicari",
    //primaryKeys = ["korisnik", "vremeKreiranja"]
)
data class TakmicarDbModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val korisnik: String,
    val rezultat: Double,
    val ukupneIgre: Int,
    val vremeKreiranja: Long
)