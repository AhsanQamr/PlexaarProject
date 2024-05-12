package com.example.plexaarproject.model

data class ContentObjectX(
    val answers: String,
    val hasOption: Boolean,
    val id: Int,
    val options: List<Any>,
    val questionText: String,
    val questionType: String,
    val regex: String
)