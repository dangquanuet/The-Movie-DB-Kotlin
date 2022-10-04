package com.example.moviedb.data.model

import com.example.moviedb.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieTest {

    @Test
    fun getFullBackdropPath_backdropPathIsNull() {
        assertEquals(
            null, Movie(id = "1").getFullBackdropPath()
        )
    }

    @Test
    fun getFullBackdropPath_backdropPathIsBlank() {
        assertEquals(
            null, Movie(id = "1", backdropPath = "   ").getFullBackdropPath()
        )
    }

    @Test
    fun getFullBackdropPath_backdropPathIsNotNullAndNotBlank() {
        val path = "123"
        assertEquals(
            BuildConfig.SMALL_IMAGE_URL + path,
            Movie(id = "1", backdropPath = path).getFullBackdropPath()
        )
    }

    @Test
    fun getFullPosterPath_posterPathIsNull() {
        assertEquals(
            null, Movie(id = "1").getFullPosterPath()
        )
    }

    @Test
    fun getFullPosterPath_posterPathIsBlank() {
        assertEquals(
            null, Movie(id = "1", posterPath = "   ").getFullPosterPath()
        )
    }

    @Test
    fun getFullPosterPath_posterPathIsNotNullAndNotBlank() {
        val path = "123"
        assertEquals(
            BuildConfig.SMALL_IMAGE_URL + path,
            Movie(id = "1", posterPath = path).getFullPosterPath()
        )
    }
}