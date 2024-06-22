package com.example.musicaz.di

import retrofit2.Call
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiService: AppModule.ModuleApi){


    suspend fun getArtists(): Call<ArtistResponse> {
        return apiService.getArtists()
    }

    suspend fun getArtistTracks(name: String): Call<ArtistTrackResponse> {
        return apiService.getArtistTracks(name = name)
    }

    suspend fun getAlbums(): Call<AlbumResponse> {
        return apiService.getAlbums()
    }
    suspend fun getTracks(): Call<TrackResponse> {
        return apiService.getTracks()
    }
}