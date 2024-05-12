package com.example.plexaarproject.model

data class Option(
    val childs: List<Child>,
    val choiceText: String,
    val dropdownQuestion: Int,
    val haschild: Boolean,
    val id: Int
)