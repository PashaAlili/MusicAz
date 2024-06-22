package com.example.musicaz.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicaz.di.AppRepository
import com.example.musicaz.di.Artist
import com.example.musicaz.di.ArtistResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AllArtistViewModel @Inject constructor(private val repository : AppRepository)  : ViewModel() {



    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> = _artists

    suspend fun fetchArtists() {
        repository.getArtists().enqueue(object : Callback<ArtistResponse> {
            override fun onResponse(call: Call<ArtistResponse>, response: Response<ArtistResponse>) {
                if (response.isSuccessful) {
                    val artistResponse = response.body()
                    _artists.postValue(artistResponse?.results)
                } else {
                    // Hata durumu
                }
            }

            override fun onFailure(call: Call<ArtistResponse>, t: Throwable) {
                // Network hatası
            }
        })
    }

}


