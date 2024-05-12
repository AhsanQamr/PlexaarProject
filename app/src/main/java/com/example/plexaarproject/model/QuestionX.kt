package com.example.plexaarproject.model

data class QuestionX(
    val answers: String,
    val contentObject: ContentObject,
    val contentType: String,
    val createdDate: String,
    val enabled: Boolean,
    val id: Int,
    val objectId: Int,
    val published: Boolean,
    val updatedDate: String,
    val version: Double
)