package com.blazze.albumapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blazze.albumapp.model.Album
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var albumDao: AlbumDao
    private lateinit var db: AppDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        albumDao = db.getAlbumDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadAlbum() = runBlocking {
        val album = listOf(Album(id = 1, title = "Album Title", userId = 1))
        albumDao.insertAllAlbums(album)
        val albums = albumDao.getAllAlbums()
        assertThat(albums, CoreMatchers.hasItem(album[0]))
    }

}