package com.example.bookin.model.lists

import com.google.gson.annotations.SerializedName

class NytBookListReceiber {
    @SerializedName("status")
    lateinit var status:String
    @SerializedName("copyright")
    lateinit var copyright : String
    @SerializedName("num_results")
    var numberOfResuls : Int = 0
    @SerializedName("results")
    lateinit var results : List<BookListName>

}