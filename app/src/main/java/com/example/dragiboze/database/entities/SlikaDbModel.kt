package com.example.dragiboze.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "slike",
    foreignKeys = [
        ForeignKey(
            entity = MacaDbModel::class,
            parentColumns = ["id"],
            childColumns = ["idRase"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SlikaDbModel (
    @PrimaryKey val id: String,
    val url: String,
    val idRase: String,
    val sirina: Int,
    val visina: Int
)