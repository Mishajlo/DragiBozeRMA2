package com.example.dragiboze.models.data

data class MacaUiModel(
    val id_mace: String,
    val ime_rase: String,
    val alt_ime: String,
    val opis: String,
    val temperament: List<String>
)

data class MacaDetailsModel(
    val id_mace: String,
    val slika_mace: MackicaSlicica? = null,
    val url_mace: String? = null,
    val ime_rase: String,
    val opis: String,
    val poreklo: String,
    val temperament: List<String>,
    val zivot: String,
    val debel: DebelaMaca,
    val affection_level: Int,
    val intelligence: Int,
    val child_friendly: Int,
    val dog_friendly: Int,
    val stranger_friendly: Int,
    val retkost: Int,
    val wikipedia: String
)

data class DebelaMaca(
    val imperial: String,
    val metric: String
)

data class MackicaSlicica(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)
