package com.example.plexaarproject.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plexaarproject.events.ApiResponse
import com.example.plexaarproject.model.PlexaarModel
import com.example.plexaarproject.repository.PlexarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val plexarRepository: PlexarRepository) : ViewModel() {

    private var _formData : MutableStateFlow<ApiResponse<PlexaarModel>> = MutableStateFlow(ApiResponse.Empty())
    val formData = _formData.asStateFlow()

    fun getForm()  {
        _formData.value = ApiResponse.Loading()
        viewModelScope.launch (Dispatchers.IO) {
            val call: Call<PlexaarModel> = plexarRepository.getForm()
            call.enqueue(object : Callback<PlexaarModel> {
                override fun onResponse(call: Call<PlexaarModel>, response: Response<PlexaarModel>) {
                    _formData.value = ApiResponse.Success(response.body()!!)
                }

                override fun onFailure(call: Call<PlexaarModel>, t: Throwable) {
                    _formData.value = t.message?.let { ApiResponse.Failure(it) }!!
                }
            })
        }
    }

}