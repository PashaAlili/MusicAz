package com.example.musicaz.di

import android.os.Parcel
import android.os.Parcelable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.jamendo.com/v3.0/"
    private const val CLIENT_ID = "ec0790a6"


    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer f7d018195c4310b237ed1e239e253ae7")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
    }.build()

    @Provides
    fun retrofitArtists(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    fun retrofitInstance(apiService:Retrofit): ModuleApi {
        return apiService.create(ModuleApi::class.java)
    }



//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()


    interface ModuleApi {

        @GET("tracks/")
        fun getTracks(): Call<TrackResponse>

        @GET("albums/")
        fun getAlbums(): Call<AlbumResponse>

        @GET("artists/")
        fun getArtists(
            @Query("client_id") clientId: String = CLIENT_ID,
            @Query("format") format: String = "jsonpretty",
        ): Call<ArtistResponse>

        // example: https://api.jamendo.com/v3.0/artists/tracks/?client_id=ec0790a6&format=jsonpretty&order=track_name_desc&album_datebetween=0000-00-00_2012-01-01&name=we+are+fm
        @GET("artists/tracks/")
        fun getArtistTracks(
            @Query("client_id") clientId: String = CLIENT_ID,
            @Query("format") format: String = "jsonpretty",
            @Query("order") order: String = "track_name_desc",
            @Query("album_datebetween") album_datebetween: String = "0000-00-00_2012-01-01",
            @Query("name") name: String,

        ): Call<ArtistTrackResponse>
    }


//    val api: ModuleApi = retrofit.create(ModuleApi::class.java)
}


data class TrackResponse(
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("results")
    val results: List<Track>,
    @SerializedName("next")
    val next: String
)

data class Track(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("artist_id")
    val artistId: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("artist_idstr")
    val artistIdstr: String,
    @SerializedName("album_id")
    val albumId: String,
    @SerializedName("album_name")
    val albumName: String,
    @SerializedName("releasedate")
    val releaseDate: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("license_ccurl")
    val licenseUrl: String,
    @SerializedName("shorturl")
    val shortUrl: String,
    @SerializedName("shareurl")
    val shareUrl: String,
    @SerializedName("audiodownload")
    val audioDownload: String,
    @SerializedName("audio")
    val audio: String,
    @SerializedName("prourl")
    val proUrl: String,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("album_image")
    val albumImageUrl: String,
    @SerializedName("audiodownload_allowed")
    val audiodownloadAllowed: Boolean,

)

data class ArtistTrackResponse(
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("results")
    val results: List<ArtistTracks>,
    @SerializedName("next")
    val next: String
)

data class ArtistTracks(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("joindate")
    val joinDate: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("tracks")
    val tracks: List<ArtistTrack>,
    )

data class ArtistTrack(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("album_id")
    val albumId: String,
    @SerializedName("album_name")
    val albumName: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("releasedate")
    val releasedate: String,
    @SerializedName("license_ccurl")
    val licenseCcurl: String,
    @SerializedName("album_image")
    val albumImage: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("audio")
    val audio: String,
    @SerializedName("audiodownload")
    val audioDownload: String,
    @SerializedName("audiodownload_allowed")
    val audioDownloadAllowed: Boolean,

)

data class ArtistResponse(
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("results")
    val results: List<Artist>,
    @SerializedName("next")
    val next: String
)

data class Artist(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("joindate")
    val joinDate: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("shorturl")
    val shorturl: String,
    @SerializedName("shareurl")
    val shareurl: String,

)

data class AlbumResponse(
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("results")
    val results: List<Album>,
    @SerializedName("next")
    val next: String
)

data class Album(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("releasedate")
    val releaseDate: String,
    @SerializedName("artist_id")
    val artistId: String,
    @SerializedName("artist_name")
    val artistName: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("zip")
    val zip: String,
    @SerializedName("shorturl")
    val shorturl: String,
    @SerializedName("shareurl")
    val shareurl: String,
    @SerializedName("zip_allowed")
    val zip_allowed: Boolean,

)

data class Headers(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("error_message")
    val errorMessage: String,
    @SerializedName("warnings")
    val warnings: String,
    @SerializedName("results_count")
    val resultsCount: Int

)
