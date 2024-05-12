package com.example.plexaarproject.model

data class PlexaarModel(
    val currentSection: Int,
    val error: Boolean,
    val message: String,
    val nextSection: Any,
    val result: Result,
    val statusCode: Int,
    val totalSection: Int
)