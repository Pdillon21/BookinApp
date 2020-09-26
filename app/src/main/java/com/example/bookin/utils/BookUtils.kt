package com.example.bookin.utils

import com.example.bookin.model.books.BookContainer

class BookUtils {

    fun getBookTitleFormated (bookContainer: BookContainer): String {
        val bookTilte : String = bookContainer.bookDetails[0].title
        var bookTitleFormatting : String = bookTilte.toLowerCase()
        bookTitleFormatting[0].toUpperCase()

        for (x in bookTitleFormatting.indices){
            if (bookTitleFormatting[x].toString()==" "){
                if (bookTitleFormatting.length<x){
                    bookTitleFormatting[x+1].toUpperCase()
                }
            }
        }

        return bookTitleFormatting
    }
}