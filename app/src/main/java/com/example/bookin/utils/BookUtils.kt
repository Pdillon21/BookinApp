package com.example.bookin.utils

import com.example.bookin.model.books.BookContainer

class BookUtils {

    fun getBookTitleFormated(bookContainer: BookContainer): String {
        val bookTilte: String = bookContainer.bookDetails[0].title
        var bookTitleFormatting: String = bookTilte.toLowerCase()
        var bookTitleFormated: String = ""
        val words = bookTitleFormatting.split(" ")
        for (word in words) {
            bookTitleFormated += word.capitalize() + " "
        }
        bookTitleFormated.trim()

        return bookTitleFormated
    }
}