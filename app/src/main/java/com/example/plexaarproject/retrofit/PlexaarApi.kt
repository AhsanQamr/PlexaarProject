package com.example.plexaarproject.retrofit

import com.example.plexaarproject.model.CurrentSection
import com.example.plexaarproject.model.PlexaarModel
import com.example.plexaarproject.model.Question
import retrofit2.Call
import retrofit2.http.GET

interface PlexaarApi {
    @GET("/genericform_svc/pb/get_form?formId=48&customerId=198&section=1")
    fun getForm(): Call<PlexaarModel>
}