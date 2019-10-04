package com.aak.books.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookService {

    private val URL = "https://api.nytimes.com"
    private val API: BookApi

    init {
        API = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(BookApi::class.java)
    }

    fun getBooks(): Single<APIResponse> {
        return API.getBooks()
    }

}