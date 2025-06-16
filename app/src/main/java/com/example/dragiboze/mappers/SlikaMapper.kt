package com.example.dragiboze.mappers

import com.example.dragiboze.database.entities.SlikaDbModel
import com.example.dragiboze.models.api.models.MackicaSlicica
import com.example.dragiboze.models.data.SlikaUiModel

fun MackicaSlicica.asSlikaDbModel(idMace: String) = SlikaDbModel(
    id = id,
    idRase = idMace,
    sirina = width,
    visina = height,
    url = url
)

fun SlikaDbModel.asSlikaUiModel() = SlikaUiModel(
    id = id,
    url = url,
    visina = visina,
    sirina = sirina
)