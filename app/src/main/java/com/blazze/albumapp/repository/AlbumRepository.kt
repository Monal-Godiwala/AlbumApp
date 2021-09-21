package com.blazze.albumapp.repository

import com.blazze.albumapp.database.AppDatabase
import com.blazze.albumapp.model.Album
import com.blazze.albumapp.network.ApiInterface
import com.blazze.albumapp.network.SafeApiRequest

class AlbumRepository(
    private val apiInterface: ApiInterface?,
    private val appDatabase: AppDatabase?
) : SafeApiRequest() {

    companion object {
        const val API_GET_ALBUMS = 101
    }

    // API Helper
    suspend fun getAlbums(): List<Album> = apiRequest { apiInterface?.getAlbums() }

    // Database Helper
    suspend fun getOfflineAlbums(): List<Album>? = appDatabase?.getAlbumDao()?.getAllAlbums()

    suspend fun insertOfflineAlbums(albums: List<Album>?) = albums?.let {
        appDatabase?.getAlbumDao()?.insertAllAlbums(albums)
    }

}