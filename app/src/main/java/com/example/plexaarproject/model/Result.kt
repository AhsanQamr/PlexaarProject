package com.example.plexaarproject.model

data class Result(
    val createdDate: String,
    val currentSection: CurrentSection,
    val formName: String,
    val id: Int,
    val published: Boolean,
    val title: String,
    val totalSections: List<TotalSection>,
    val updatedDate: String,
    val version: Double
)