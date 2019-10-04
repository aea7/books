package com.aak.books.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.aak.books.R
import com.aak.books.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val booksAdapter = BookListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        bookList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = booksAdapter
        }

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    // Observe any changes to Mutable Live Data objects and update accordingly.

    fun observeViewModel() {
        viewModel.books.observe(this, Observer { books ->
            books?.let {
                bookList.visibility = View.VISIBLE
                booksAdapter.updateBooks(it)
            }
        })

        viewModel.bookLoadErrorMessage.observe(this, Observer { message ->
            val errorMessage = "Error: $message"
            message?.let { errorView.text = errorMessage }
        })

        viewModel.bookLoadError.observe(this, Observer { isError ->
            isError?.let {
                errorView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                isLoadingBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    errorView.visibility = View.GONE
                    bookList.visibility = View.GONE
                }
            }
        })

    }
}
