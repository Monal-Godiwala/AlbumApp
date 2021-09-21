package com.blazze.albumapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blazze.albumapp.network.ApiListener
import com.blazze.albumapp.repository.AlbumRepository

class AlbumViewModelFactory(
    private val repository: AlbumRepository?,
    private val apiListener: ApiListener
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(repository, apiListener) as T
    }

}