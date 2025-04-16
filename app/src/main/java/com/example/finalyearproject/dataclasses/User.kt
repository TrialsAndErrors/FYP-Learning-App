package com.example.finalyearproject

import java.io.Serializable

data class User(
    val Username: String,
    val Password: String,
    val Style: String,
    val Fontsize: Float,
    val Speechrate: Float,
    val Role: String,
    val OOPscore: Int,
    val MALscore: Int
) : Serializable
