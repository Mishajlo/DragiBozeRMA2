package com.example.dragiboze.mappers

import com.example.dragiboze.database.entities.TakmicarDbModel
import com.example.dragiboze.models.api.models.TakmicarApiModel

fun TakmicarApiModel.asTakmicarDbModel(ukupneIgre: Int) = TakmicarDbModel(
    korisnik = nickname,
    rezultat = result,
    vremeKreiranja = createdAt,
    ukupneIgre = ukupneIgre
)