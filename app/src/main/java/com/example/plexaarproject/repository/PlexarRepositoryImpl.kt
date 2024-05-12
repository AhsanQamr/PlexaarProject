package com.example.plexaarproject.repository

import com.example.plexaarproject.model.CurrentSection
import com.example.plexaarproject.model.PlexaarModel
import com.example.plexaarproject.model.Question
import com.example.plexaarproject.retrofit.PlexaarApi
import retrofit2.Call
import javax.inject.Inject

class PlexarRepositoryImpl @Inject constructor(private var plexarApi: PlexaarApi) : PlexarRepository {
    override fun getForm() : Call<PlexaarModel> {
        return plexarApi.getForm()
    }
}
