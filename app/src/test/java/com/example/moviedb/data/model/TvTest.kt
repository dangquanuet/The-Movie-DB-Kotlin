package com.example.moviedb.data.model

import com.example.moviedb.BuildConfig
import org.junit.Assert
import org.junit.Test

class TvTest {

    @Test
    fun getFullPosterPath_posterPathIsNull() {
        Assert.assertEquals(
            null, Tv(id = "1").getFullPosterPath()
        )
    }

    @Test
    fun getFullPosterPath_posterPathIsBlank() {
        Assert.assertEquals(
            null, Tv(id = "1", posterPath = "   ").getFullPosterPath()
        )
    }

    @Test
    fun getFullPosterPath_posterPathIsNotNullAndNotBlank() {
        val path = "123"
        Assert.assertEquals(
            BuildConfig.SMALL_IMAGE_URL + path,
            Tv(id = "1", posterPath = path).getFullPosterPath()
        )
    }
}