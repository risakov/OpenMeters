package ru.webant.domain.entities

data class ResponseEntity<T>(
    val result: List<T>
)