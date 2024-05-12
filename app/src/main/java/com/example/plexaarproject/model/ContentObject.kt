package com.example.plexaarproject.model

data class ContentObject(
    val answers: String,
    val hasOption: Boolean,
    val id: Int,
    val options: List<Option>,
    val questionText: String,
    val questionType: String,
    val regex: String
)