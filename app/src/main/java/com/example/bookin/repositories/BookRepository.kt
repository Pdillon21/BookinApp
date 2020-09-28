package com.example.bookin.repositories


import androidx.lifecycle.MutableLiveData
import com.example.bookin.model.books.NytListeReceiber
import com.example.bookin.model.lists.NytBookListReceiber
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BookRepository {
    val BASE_URL: String = "https://api.nytimes.com/svc/books/v3/"
    val key: String = "t5uQAA184WRFQdfkhVzRsAAkEPALjdOX"

    var bookListResponse: MutableLiveData<NytBookListReceiber> = MutableLiveData()
    var bookListByGenre: MutableLiveData<NytListeReceiber> = MutableLiveData()

    fun getBooksByGenre(genreName: String) {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()

        val service = retrofit.create(BookService::class.java)

        service.getBooks(genreName, key).enqueue(object : Callback<NytListeReceiber> {
            override fun onFailure(call: Call<NytListeReceiber>, t: Throwable) {
                bookListByGenre.value = null
            }

            override fun onResponse(
                call: Call<NytListeReceiber>,
                response: Response<NytListeReceiber>
            ) {
                if (response.isSuccessful) {
                    bookListByGenre.value = response.body()
                } else {
                    bookListByGenre.value = null
                }
            }

        })
    }


    fun getBookList() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(BookService::class.java)

        service.getBookLists(key).enqueue(object : Callback<NytBookListReceiber> {
            override fun onFailure(call: Call<NytBookListReceiber>, t: Throwable) {
                bookListResponse.value = null
            }

            override fun onResponse(
                call: Call<NytBookListReceiber>,
                response: Response<NytBookListReceiber>
            ) {
                if (response.isSuccessful) {
                    bookListResponse.value = response.body()
                } else {
                    bookListResponse.value = null
                }
            }


        })


    }


    fun manageErrors(response: Response<Unit>) {

    }


}