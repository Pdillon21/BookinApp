package com.example.bookin.repositories

import com.example.bookin.model.books.NytListeReceiber
import com.example.bookin.model.lists.NytBookListReceiber
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("lists.json")
    fun getBooks(
        @Query("list") list: String,
        @Query("api-key") key: String
    ): Call<NytListeReceiber>

    @GET("lists/names")
    fun getBookLists (
        @Query ("api-key") key:String
    ):Call<NytBookListReceiber>

}