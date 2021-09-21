package com.blazze.albumapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.blazze.albumapp.model.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM Album")
    suspend fun getAllAlbums(): List<Album>?

    @Insert
    suspend fun insertAllAlbums(albums: List<Album>)

}