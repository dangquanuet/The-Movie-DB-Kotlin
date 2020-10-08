package com.example.moviedb.data.model

import com.example.moviedb.BuildConfig
import org.junit.Assert
import org.junit.Test

class CastTest {
    @Test
    fun getFullProfilePath_profilePathIsNull() {
        Assert.assertEquals(
            null, Cast(id = "1").getFullProfilePath()
        )
    }

    @Test
    fun getFullProfilePath_profilePathIsBlank() {
        Assert.assertEquals(
            null, Cast(id = "1", profile_path = "   ").getFullProfilePath()
        )
    }

    @Test
    fun getFullProfilePath_profilePathIsNotNullAndNotBlank() {
        val path = "123"
        Assert.assertEquals(
            BuildConfig.SMALL_IMAGE_URL + path,
            Cast(id = "1", profile_path = path).getFullProfilePath()
        )
    }
}