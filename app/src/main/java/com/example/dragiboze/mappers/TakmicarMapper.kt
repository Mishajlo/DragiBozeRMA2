package com.example.dragiboze.mappers

import com.example.dragiboze.database.entities.TakmicarDbModel
import com.example.dragiboze.models.api.models.TakmicarApiModel
import com.example.dragiboze.models.data.TakmicarUiModel

fun TakmicarApiModel.asTakmicarDbModel(ukupneIgre: Int) = TakmicarDbModel(
    korisnik = nickname,
    rezultat = result,
    vremeKreiranja = createdAt,
    ukupneIgre = ukupneIgre
)

fun TakmicarApiModel.asTakmicarUiModel(ukupneIgre: Int?) = TakmicarUiModel(
    korisnik = nickname,
    rezultat = result,
    vremeKreiranja = createdAt,
    ukupneIgre = ukupneIgre ?: 0,
    kategorija = category
)