package com.example.bookin.entities

import com.google.gson.annotations.SerializedName

class SerpApiContainer {

    @SerializedName("images_results")
    lateinit var results: List<SerpiImageResult>

}