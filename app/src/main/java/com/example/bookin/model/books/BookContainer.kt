package com.example.bookin.model.books

import com.google.gson.annotations.SerializedName

class BookContainer {
    @SerializedName("list_name")
    lateinit var listName : String
    @SerializedName("display_name")
    lateinit var displayName : String
    @SerializedName("bestsellers_date")
    lateinit var bestSellerDate : String
    @SerializedName("published_date")
    lateinit var publishedDate : String
    @SerializedName("rank")
    var rank : Int = 0
    @SerializedName("rank_last_week")
    var rankLastWeek : Int = 0
    @SerializedName("weeks_on_list")
    var weeksOnList : Int = 0
    @SerializedName("asterisk")
    var asterisk : Int = 0
    @SerializedName("dagger")
    var dagger : Int = 0
    @SerializedName("amazon_product_url")
    lateinit var amazonLink : String
    @SerializedName("isbns")
    lateinit var isbns : List<Isbns>
    @SerializedName("book_details")
    lateinit var bookDetails : List<BookDetails>
    @SerializedName("reviews")
    lateinit var reviews : List<Reviews>




    inner class Isbns{
        @SerializedName("isbn10")
        lateinit var isbn10 : String
        @SerializedName("isbn13")
        lateinit var isbn13 : String
    }

    inner class Reviews {
        @SerializedName("book_review_link")
        lateinit var bookReviewLink : String
        @SerializedName("first_chapter_link")
        lateinit var firsChapterLink : String
        @SerializedName("sunday_review_link")
        lateinit var sundayReviewLink : String
        @SerializedName("article_chapter_link")
        lateinit var articleChapterLink : String
    }
}
