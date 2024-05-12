package com.example.plexaarproject.model

data class CurrentSection(
    val id: Int,
    val position: Int,
    val questions: List<Question>,
    val sectionTitle: String
)