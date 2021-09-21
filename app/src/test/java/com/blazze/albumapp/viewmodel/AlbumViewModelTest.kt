package com.blazze.albumapp.viewmodel

import com.blazze.albumapp.MockResponseFileReader
import com.blazze.albumapp.network.ApiClient
import com.blazze.albumapp.network.ApiInterface
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class AlbumViewModelTest {

    private lateinit var mockWebServer: MockWebServer

    private var apiInterface: ApiInterface? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiInterface = ApiClient.client?.create(ApiInterface::class.java)

    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        Assert.assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response Code 200 returned`() {

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)

        val actualResponse = runBlocking { apiInterface?.getAlbums() }

        assertEquals(response.status.contains("200"), actualResponse?.code() == 200)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}