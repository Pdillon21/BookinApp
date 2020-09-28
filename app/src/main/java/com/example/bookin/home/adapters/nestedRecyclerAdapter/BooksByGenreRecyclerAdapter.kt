package com.example.bookin.home.adapters.nestedRecyclerAdapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.bookin.R
import com.example.bookin.model.books.BookContainer
import com.example.bookin.repositories.GoogleImageRepository
import com.example.bookin.utils.BookUtils
import kotlinx.android.synthetic.main.book_recycler_cell.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.http.Url

class BooksByGenreRecyclerAdapter(var context: Context, var booklist: List<BookContainer>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder(
            LayoutInflater.from(context).inflate(R.layout.book_recycler_cell, parent, false),
            context
        )
    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val thisViewHolder: BookViewHolder = holder as BookViewHolder
        thisViewHolder.bookTitleTv.setText(BookUtils().getBookTitleFormated(booklist[position]))
        performGlideAttempt(booklist[position], holder)
    }

    fun performGlideAttempt(bookContainer: BookContainer, holder: BookViewHolder) {
        val imageUrl: String? = getImageUrl(bookContainer)
        if (imageUrl.isNullOrBlank()) {
            failedToLoadImage(holder)
        } else {
            try {
                Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .error(R.drawable.ic_warning_white)
                    .fallback(R.drawable.ic_warning_white)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            failedToLoadImage(holder)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageLoaded(holder)
                            return false
                        }

                    })
                    .into(holder.bookImageIV)

            } catch (e: java.lang.Exception) {
                failedToLoadImage(holder)
            }
        }

    }

    fun failedToLoadImage(holder: BookViewHolder) {
        holder.imageLoadingContainer.visibility = View.GONE
        holder.bookImageIV.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.bookImageIV.setImageResource(R.drawable.ic_warning_white)
    }

    fun imageLoaded(holder: BookViewHolder) {
        holder.imageLoadingContainer.visibility = View.GONE
    }

    fun getImageUrl(bookContainer: BookContainer): String? {
        val isbn10: String = bookContainer.bookDetails[0].primaryIsbn10
        val isbn13: String = bookContainer.bookDetails[0].primaryIsbn13
        if (!isbn10.isBlank()) {
            return "http://covers.openlibrary.org/b/isbn/$isbn10-M.jpg"
        } else if (!isbn13.isBlank()) {
            return "http://covers.openlibrary.org/b/isbn/$isbn13-M.jpg"
        } else {
            return null
        }
    }


    class BookViewHolder constructor(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        val bookTitleTv: TextView = itemView.bookTitleTextView
        val bookImageIV: ImageView = itemView.bookImageView
        val imageLoadingContainer: ConstraintLayout = itemView.imageLoadingContainer
    }

}