package com.example.bookin.entities

import com.google.gson.annotations.SerializedName

class SerpiImageResult {

    @SerializedName("position")
    lateinit var position: String

    @SerializedName("thumbnail")
    lateinit var thumbnail: String

    @SerializedName("original")
    lateinit var original: String

    @SerializedName("source")
    lateinit var source: String

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("link")
    lateinit var link: String

}