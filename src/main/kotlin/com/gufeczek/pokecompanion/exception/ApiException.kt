package com.gufeczek.pokecompanion.exception

data class ApiException(
    val status: Int,
    val error: String,
    val message: String,
    val timeStamp: String
)
