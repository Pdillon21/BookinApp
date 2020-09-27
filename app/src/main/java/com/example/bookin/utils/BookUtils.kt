package com.example.bookin.utils

import com.example.bookin.model.books.BookContainer

class BookUtils {

    fun getBookTitleFormated (bookContainer: BookContainer): String {
        val bookTilte : String = bookContainer.bookDetails[0].title
        var bookTitleFormatting : String = bookTilte.toLowerCase()

        val words = bookTitleFormatting.split(" ")
        var bookTilteFormated = ""
        for (word in words){
            bookTilteFormated += word.capitalize() + " "
        }
        bookTilteFormated = bookTilteFormated.trim()
        return bookTilteFormated
    }
}