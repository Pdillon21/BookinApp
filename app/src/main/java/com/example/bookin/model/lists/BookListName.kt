package com.example.bookin.model.lists

import com.google.gson.annotations.SerializedName

class BookListName {
    @SerializedName("list_name")
    lateinit var listName : String
    @SerializedName("display_name")
    lateinit var displayName : String
    @SerializedName("list_name_encoded")
    lateinit var encodedListName : String
    @SerializedName("oldest_published_date")
    lateinit var oldestPublishedDate : String
    @SerializedName("newest_published_date")
    lateinit var newestPublishedDate : String
    @SerializedName("updated")
    lateinit var update : String
}