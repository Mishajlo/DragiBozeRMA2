package com.example.dragiboze.models.api.models
import kotlinx.serialization.Serializable

/*data class MacApiModel(
    val id: Int,
    val breedName: String,
    val traits: List<String>
)*/

@Serializable
data class MacApiModel(
    val weight: DebelaMaca,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    val alt_names: String = "",
    val reference_image_id: String? = null,
    val image: MackicaSlicica? = null,
    val life_span: String,
    val rare: Int,
    val affection_level: Int,
    val intelligence: Int,
    val child_friendly: Int,
    val dog_friendly: Int,
    val stranger_friendly: Int,
    val wikipedia_url: String? = null,
    val imageUrl: String? = null,
)

@Serializable
data class DebelaMaca(
    val imperial: String,
    val metric: String
)

@Serializable
data class MackicaSlicica(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)
