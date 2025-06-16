package com.example.dragiboze.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mace")
data class MacaDbModel (

    @PrimaryKey val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val alt_names: String,
    val reference_image_id: String,
    val life_span: String,
    val rare: Int,
    val affection_level: Int,
    val intelligence: Int,
    val child_friendly: Int,
    val dog_friendly: Int,
    val stranger_friendly: Int,
    val wikipedia_url: String,
    val imageUrl: String,
    val imperial: String,
    val metric: String

)