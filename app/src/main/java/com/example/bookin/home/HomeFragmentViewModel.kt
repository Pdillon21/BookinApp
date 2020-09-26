package com.example.bookin.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookin.model.NytError
import com.example.bookin.model.books.NytListeReceiber
import com.example.bookin.model.lists.BookListName
import com.example.bookin.model.lists.NytBookListReceiber
import com.example.bookin.repositories.BookRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class HomeFragmentViewModel : ViewModel() {
    lateinit var context: HomeFragment
    var bookLists: MutableLiveData<NytBookListReceiber> = MutableLiveData()
    var listsToSearch: MutableLiveData<MutableList<BookListName>> = MutableLiveData()
    var allBooksInLists: MutableLiveData<MutableList<NytListeReceiber>> = MutableLiveData()

    var allListForHomeDefined: MutableLiveData<Boolean> = MutableLiveData(false)
    var allBooksFetched : MutableLiveData<Boolean> = MutableLiveData()
    private val repo: BookRepository = BookRepository()


    fun init() {
        initObservers()
        getBooksFromService()
    }

    private fun initObservers() {
        repo.bookListResponse.observe(context, Observer {
            it
            if (it != null) {
                bookLists.value = it
            } else {
                bookLists.value = null
            }
        })
        repo.bookListByGenre.observe(context, Observer {
            it
            if (allBooksInLists.value == null){
                allBooksInLists.value = ArrayList()
            }
            if (it != null) {
                allBooksInLists.value!!.add(it)
                if (!allBooksInLists.value.isNullOrEmpty()){
                    if (listsToSearch.value!!.size==allBooksInLists.value!!.size){
                        allBooksFetched.value = true
                    }
                }
            }

        })
    }

    private fun getBooksFromService() {
        repo.getBookList()
    }

    fun getRandomListsForHome(numberOfLists: Int) {
        var indexes: MutableList<Int> = ArrayList()
        var fullListLength: Int = bookLists.value!!.results.size
        var listToAssign: MutableList<BookListName> = ArrayList()
        while (indexes.size < numberOfLists) {
            var thisIndex: Int = Random.nextInt(0, fullListLength)
            if (!indexes.contains(thisIndex)) {
                indexes.add(thisIndex)
            }
        }
        for (index in indexes) {
            listToAssign.add(bookLists.value!!.results[index])
        }
        listsToSearch.value = listToAssign
        allListForHomeDefined.value = true

    }

    fun getBookLists() {
        var listToSearch = listsToSearch.value
        if (!listToSearch.isNullOrEmpty()) {
            for (list in listToSearch) {
                repo.getBooksByGenre(list.encodedListName)
            }
        }
    }


    private fun getListsForHome() {

    }

    private fun failedToBringBookData() {
        context.failedToBringBookData()
    }
}