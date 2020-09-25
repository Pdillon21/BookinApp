package com.example.bookin.home.adapters.nestedRecyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookin.R
import com.example.bookin.model.books.BookContainer
import com.example.bookin.model.books.NytListeReceiber
import kotlinx.android.synthetic.main.custom_book_list_recycler_container.view.genreRecylcer
import kotlinx.android.synthetic.main.genre_recycler_cell.view.*

class GenreRecyclerAdapter(val context: Context, val booksForMain: MutableList<NytListeReceiber>) :
    RecyclerView.Adapter<GenreRecyclerAdapter.MainViewHolder>() {

    class MainViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var genreNameTv: TextView
        var genreRecyclerView: RecyclerView

        init {
            genreNameTv = itemView.genreName
            genreRecyclerView = itemView.genreRecylcer
        }

        fun bind(genreContainer: NytListeReceiber) {
            genreNameTv.setText(genreContainer.results[0].listName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.genre_recycler_cell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return booksForMain.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(booksForMain[position])
        setGenreRecycler(booksForMain[position].results, holder.genreRecyclerView)

    }

    fun setGenreRecycler(books: List<BookContainer>, recycler: RecyclerView) {
        var adapter: BooksByGenreRecyclerAdapter = BooksByGenreRecyclerAdapter(context, books)
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.adapter = adapter
    }
}