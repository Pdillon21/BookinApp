package com.example.bookin.model.books

import com.google.gson.annotations.SerializedName
import java.util.*

class NytListeReceiber {

    @SerializedName("status")
    lateinit var status:String
    @SerializedName("copyright")
    lateinit var copyrigth : String
    @SerializedName("num_results")
    lateinit var numerOfResults : String
    @SerializedName("last_modified")
    lateinit var lastModifiees : Date
    @SerializedName("results")
    lateinit var results : List<BookContainer>


}