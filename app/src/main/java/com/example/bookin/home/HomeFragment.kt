package com.example.bookin.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookin.R
import com.example.bookin.entities.BookinActivity
import com.example.bookin.home.adapters.nestedRecyclerAdapter.GenreRecyclerAdapter
import com.example.bookin.main.MainActivity
import com.example.bookin.model.books.NytListeReceiber
import com.example.bookin.model.lists.BookListName
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    lateinit var viewModel: HomeFragmentViewModel
    lateinit var baseActivity: BookinActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        viewModel.context = this
        baseActivity = this.activity as BookinActivity

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        showProgressBar()
        initObservers()
        viewModel.init()
    }

    private fun showProgressBar (){
        homeFragmentProgressbar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        homeFragmentProgressbar.visibility = View.GONE
    }

    private fun showEmptyListView (){
        emptyBookListView.visibility = View.VISIBLE
    }
    private fun initObservers() {
        viewModel.bookLists.observe(this, Observer {it
            if (it!=null && it.results.isNotEmpty()){
                viewModel.getRandomListsForHome(4)
            } else {
                // Todo se podría agregar para obtenga otra categoria y la traiga en su lugar
                print("error")
            }

        })

        viewModel.allListForHomeDefined.observe(this, Observer {allListsForHomeDefined ->
            if (allListsForHomeDefined){
                viewModel.getBookLists()
            }
        })

        viewModel.allBooksFetched.observe(this, Observer { allBooksFetched ->
            if (allBooksFetched){
                createRecyclersForList(viewModel.allBooksInLists.value!!)
            }
        })
    }

    private fun createRecyclersForList(listOfBookClasses: MutableList<NytListeReceiber>) {
        if (!listOfBookClasses.isNullOrEmpty()){
            hideProgressBar()
            genreRecylcerMain.visibility = View.VISIBLE
            val mainRecyclerAdapter : GenreRecyclerAdapter = GenreRecyclerAdapter(requireContext(),listOfBookClasses)
            val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            genreRecylcerMain.layoutManager = layoutManager
            genreRecylcerMain.adapter = mainRecyclerAdapter
        } else {
            hideProgressBar()
            showEmptyListView()
        }
    }

    fun failedToBringBookData() {
        genreRecylcerMain.visibility = View.GONE
        emptyBookListView.visibility = View.VISIBLE
        baseActivity.stopProgressDialog()
    }

    companion object {
        fun createInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
