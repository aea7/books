package com.aak.books.model

import io.reactivex.Single
import retrofit2.http.GET

interface BookApi {

    @GET("/svc/books/v3/lists/2019-09-01/combined-print-and-e-book-fiction.json?api-key=3GiIylwhoiL0USYYl7IIMDwjqe5FNtqR")
    fun getBooks():Single<APIResponse>

}