package ru.webant.domain.entities

data class ResponseEntity<T>(
    val data: List<T>
)