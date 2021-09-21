package com.blazze.albumapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blazze.albumapp.model.Album

@Database(entities = [Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAlbumDao(): AlbumDao

    companion object {

        private const val DATABASE_NAME = "database_album"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()


        fun getInstance(context: Context?): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    instance = context?.applicationContext?.let {
                        Room.databaseBuilder(it, AppDatabase::class.java, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }
    }

}