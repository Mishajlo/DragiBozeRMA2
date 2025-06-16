package com.example.dragiboze.mappers

import com.example.dragiboze.database.entities.SlikaDbModel
import com.example.dragiboze.models.api.models.MackicaSlicica

fun MackicaSlicica.asSlikaDbModel(idMace: String) = SlikaDbModel(
    id = id,
    idRase = idMace,
    sirina = width,
    visina = height,
    url = url
)