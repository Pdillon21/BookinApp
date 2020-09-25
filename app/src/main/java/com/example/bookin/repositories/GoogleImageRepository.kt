package com.example.bookin.repositories

import androidx.lifecycle.MutableLiveData
import com.example.bookin.entities.SerpApiContainer
import com.example.bookin.model.books.BookContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GoogleImageRepository {
    val BASE_URL : String = "https://serpapi.com/"
    // This is a required filled and value by the API - changing it will result on a request failure
    val FIXED_TBN_ATTR : String = "isch"
    // This refers to the pagination of google image search results, only the first set of result will be needed for this app
    val FIXED_IJN_ATTR : String = "0"

    var googleImagesResponse : MutableLiveData<SerpApiContainer> = MutableLiveData()

    fun getQueryParameterForBook (bookContainer: BookContainer) : String{
        var stringForQuery : String = ""
        stringForQuery = bookContainer.bookDetails[0].title + "by" + bookContainer.bookDetails[0].author + "book cover"
        return stringForQuery
    }

    @Synchronized
    fun getGoogleImagesByStringQuery (query : String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service : GoogleImageService = retrofit.create(GoogleImageService::class.java)
        service.getGoogleImagesForStringQuery(query,FIXED_TBN_ATTR,FIXED_IJN_ATTR).enqueue(object : Callback<SerpApiContainer>{
            override fun onResponse(
                call: Call<SerpApiContainer>,
                response: Response<SerpApiContainer>
            ) {
                if (response.isSuccessful){
                    googleImagesResponse.value = response.body()
                } else {
                    googleImagesResponse.value = null
                }
            }

            override fun onFailure(call: Call<SerpApiContainer>, t: Throwable) {
                googleImagesResponse.value = null
            }

        })
    }
}