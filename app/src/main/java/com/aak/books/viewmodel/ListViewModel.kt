package com.aak.books.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aak.books.model.Book
import com.aak.books.model.BookService
import com.aak.books.model.APIResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    // This service is used to fetch data from the books from the api with Retrofit
    private val bookService = BookService()

    // When the viewmodel is closed, we close the connection with disposable
    private val disposable = CompositeDisposable()

    private var isRefreshed = false

    // Mutable Live Data, updated asynchronously
    val books = MutableLiveData<List<Book>>()
    val bookLoadError = MutableLiveData<Boolean>()
    val bookLoadErrorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        getBooks(isRefreshed)
    }

    // Fetching the books from the api

    private fun getBooks(refreshed: Boolean) {
        isLoading.value = true
        disposable.add(bookService.getBooks()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<APIResponse> (){
                override fun onSuccess(bookList: APIResponse) {
                    if (refreshed){
                        val listOfBooks = bookList.listOfBooks?.bookList
                        books.value = listOfBooks?.sortedWith(compareByDescending { it.weeksOnList })
                    }else{
                        books.value = bookList.listOfBooks?.bookList
                    }
                    isRefreshed = !isRefreshed
                    isLoading.value = false
                    bookLoadError.value = false
                }

                override fun onError(error: Throwable) {
                    bookLoadError.value = true
                    bookLoadErrorMessage.value = error.message
                    isLoading.value = false
                }
            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}