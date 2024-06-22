package com.example.musicaz.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicaz.di.AppRepository

import com.example.musicaz.di.ArtistTrack
import com.example.musicaz.di.ArtistTrackResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class ArtistTracksViewModel @Inject constructor(private val repository : AppRepository) : ViewModel() {



    private val _artists = MutableLiveData<List<ArtistTrack>>()
    val artists: LiveData<List<ArtistTrack>> = _artists

    fun fetchArtistTracks(name: String) {
        viewModelScope.launch {


            repository.getArtistTracks(name).enqueue(object : Callback<ArtistTrackResponse> {
                override fun onResponse(
                    call: Call<ArtistTrackResponse>,
                    response: Response<ArtistTrackResponse>
                ) {
                    if (response.isSuccessful) {
                        val artistTracksResponse = response.body()
                        if (artistTracksResponse?.results!!.count() > 0) {
                            _artists.postValue(artistTracksResponse?.results?.first()?.tracks)
                        }
                    } else {
                        // Hata durumu
                    }
                }

                override fun onFailure(call: Call<ArtistTrackResponse>, t: Throwable) {

                }
            })
        }
    }

}

object SelectedArtistSongs {
    var myList: MutableList<ArtistTrack> = mutableListOf()

    fun addItem(songs: List<ArtistTrack>) {
        myList.addAll(songs)
    }

    fun removeItem() {
        myList = mutableListOf()
    }

}