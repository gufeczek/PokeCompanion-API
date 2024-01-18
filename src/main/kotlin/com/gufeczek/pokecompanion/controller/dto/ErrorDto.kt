package com.gufeczek.pokecompanion.controller.dto

data class ErrorDto(
    val status: Int,
    val error: String,
    val message: String,
    val timeStamp: String
)
