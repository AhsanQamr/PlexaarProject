package com.example.plexaarproject.listeners

interface SpinnerListener {
    fun onItemSelected(value: String, objectId: Int, position: Int)
    fun onNothingSelected()
}