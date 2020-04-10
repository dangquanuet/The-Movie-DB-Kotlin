package com.example.moviedb.data.api

/*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.data.remote.ApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(JUnit4::class)
class MovieDBServiceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var service: ApiService

    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<ApiService>(ApiService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetMovie() {
        enqueueResponse("movie_list_response.json")
        var response = service.getMovieListAsync()
        var request: RecordedRequest = mockWebServer.takeRequest()
        assertEquals(request.path, "3/discover/movie")
        assertNotNull(response)
        response.test().assertValue {
            it.results.size == 20
            it.results.get(1).id == "269149"
        }
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String, headers: Map<String, String>? = null) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/" + fileName)
        assertNotNull(inputStream)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        if (headers != null) {
            for ((key, value) in headers) {
                mockResponse.addHeader(key, value)
            }
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}*/
