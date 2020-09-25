package com.example.bookin.repositories

import com.example.bookin.entities.SerpApiContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleImageService {

    @GET("/search")
    fun getGoogleImagesForStringQuery (
        @Query("q") queryString : String,
        @Query("tbn") tbn : String,
        @Query("ijn") ijn : String
    ) : Call<SerpApiContainer>
}