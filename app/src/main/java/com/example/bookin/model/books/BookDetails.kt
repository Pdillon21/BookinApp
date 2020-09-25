package com.example.bookin.model.books

import com.google.gson.annotations.SerializedName

class BookDetails() {
    @SerializedName("title")
    lateinit var title: String
    @SerializedName("description")
    lateinit var descriptor: String
    @SerializedName("contributor")
    lateinit var contributor: String
    @SerializedName("author")
    lateinit var author: String
    @SerializedName("contributor_note")
    lateinit var contributorNote : String
    @SerializedName("price")
    var price : Int = 0
    @SerializedName("age_group")
    lateinit var ageGroup : String
    @SerializedName("publisher")
    lateinit var publisher : String
    @SerializedName("primary_isbn13")
    lateinit var primaryIsbn13 : String
    @SerializedName("primary_isbn10")
    lateinit var primaryIsbn10 : String


}