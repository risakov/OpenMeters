package ru.webant.domain.models

data class ResponseEntity<T>(
    val result: List<T>
)