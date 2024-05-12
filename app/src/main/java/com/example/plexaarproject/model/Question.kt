package com.example.plexaarproject.model

data class Question(
    val position: Int,
    val question: QuestionX,
    val required: Boolean
) {
    companion object{
        const val TYPE_NORMAL = 0
        const val TYPE_WITH_CHILD = 1
    }
}