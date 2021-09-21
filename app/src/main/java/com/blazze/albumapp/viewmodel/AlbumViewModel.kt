package com.blazze.albumapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blazze.albumapp.model.Album
import com.blazze.albumapp.network.ApiListener
import com.blazze.albumapp.repository.AlbumRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumViewModel(
    private val repository: AlbumRepository?,
    private val apiListener: ApiListener
) : ViewModel() {

    companion object {
        private const val CODE_EXCEPTION = 1000
    }

    private val _albumList = MutableLiveData<List<Album>?>()
    val albumList: LiveData<List<Album>?>
        get() = _albumList


    // Handle exception while calling api
    private fun ApiListener.exceptionHandler() =
        CoroutineExceptionHandler { _, exception ->
            exception.message?.let { message ->
                onFailure(CODE_EXCEPTION, message)
            }
        }

    private suspend fun updateAlbumListSuccess(albums: List<Album>?) {
        withContext(Dispatchers.Main) {
            _albumList.value = albums
        }
    }

    private suspend fun updateAlbumListFailure(e: Exception) {
        withContext(Dispatchers.Main) {
            e.message?.let {
                _albumList.value = null
                apiListener.onFailure(AlbumRepository.API_GET_ALBUMS, it)
            }
        }
    }

    // Fetching albums
    fun getAlbums(isOffline: Boolean) {
        apiListener.onStarted(AlbumRepository.API_GET_ALBUMS)
        viewModelScope.launch(Dispatchers.IO + apiListener.exceptionHandler()) {
            try {
                if (isOffline) {
                    // Fetch via database
                    updateAlbumListSuccess(repository?.getOfflineAlbums())
                } else {
                    // Fetch via API
                    updateAlbumListSuccess(repository?.getAlbums())
                }

            } catch (e: Exception) {
                updateAlbumListFailure(e)
            }
        }
    }

    // Adding albums to database
    fun insertOfflineAlbums(albums: List<Album>?) {
        viewModelScope.launch {
            try {
                repository?.insertOfflineAlbums(albums)
            } catch (e: Exception) {
            }
        }
    }

}