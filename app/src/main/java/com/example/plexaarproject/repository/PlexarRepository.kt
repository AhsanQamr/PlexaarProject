package com.example.plexaarproject.repository

import com.example.plexaarproject.model.CurrentSection
import com.example.plexaarproject.model.PlexaarModel
import com.example.plexaarproject.model.Question
import retrofit2.Call

interface PlexarRepository {

    fun getForm() : Call<PlexaarModel>
}