package com.example.dragiboze.datastore

import kotlinx.serialization.Serializable

@Serializable
class UserData (
    val ime: String = "",
    val prezime: String = "",
    val korisnickoIme: String = "",
    val email: String = ""
)