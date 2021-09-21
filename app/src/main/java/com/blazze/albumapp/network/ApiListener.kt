package com.blazze.albumapp.network

interface ApiListener {

    fun onStarted(requestCode: Int)
    fun onFailure(requestCode: Int, message: String)

}