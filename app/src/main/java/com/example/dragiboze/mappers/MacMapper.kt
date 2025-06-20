package com.example.dragiboze.mappers

import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.models.api.models.MacApiModel
import com.example.dragiboze.models.data.MacaUiModel

fun MacApiModel.asMacaDbModel() = MacaDbModel(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    alt_names = alt_names,
    reference_image_id = reference_image_id.toString(),
    life_span = life_span,
    rare = rare,
    affection_level = affection_level,
    intelligence = intelligence,
    child_friendly = child_friendly,
    dog_friendly = dog_friendly,
    stranger_friendly = stranger_friendly,
    wikipedia_url = wikipedia_url.toString(),
    imageUrl = image?.url.toString(),
    imperial = weight.imperial,
    metric = weight.metric
)

fun MacaDbModel.asMacaUiModel() = MacaUiModel(
    id_mace = id,
    ime_rase = name,
    alt_ime = alt_names,
    opis = description,
    temperament = temperament.split(",")
)

