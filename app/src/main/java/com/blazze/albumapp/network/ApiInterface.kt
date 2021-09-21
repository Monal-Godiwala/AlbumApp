package com.blazze.albumapp.network

import com.blazze.albumapp.model.Album
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/albums")
    suspend fun getAlbums(): Response<List<Album>>

}