package com.aak.books.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aak.books.R
import com.aak.books.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

// Adapter class for the list of books

class BookListAdapter(var books: ArrayList<Book>) :
    RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {


    // When Mutable Live Data objects have changed, update accordingly.

    fun updateBooks(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    // Populate the book item view

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.title
        private val author = view.author
        private val weeks = view.weeks
        private val rank = view.rank

        fun bind(book: Book) {
            title.text = book.title
            author.text = book.author
            weeks.text = "Weeks: ${book.weeksOnList.toString()}"
            rank.text = "Rank: ${book.rank.toString()}"

        }

    }

}