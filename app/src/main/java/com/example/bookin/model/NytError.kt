package com.example.bookin.model

import com.example.bookin.model.books.BookContainer
import com.google.gson.annotations.SerializedName

class NytError {
    @SerializedName("status")
    lateinit var status : String
    @SerializedName("copyright")
    lateinit var copyright : String
    @SerializedName("errors")
    lateinit var errors : List<String>
    @SerializedName("results")
    lateinit var results : List<BookContainer>
}