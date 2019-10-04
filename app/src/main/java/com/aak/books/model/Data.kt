package com.aak.books.model

import com.google.gson.annotations.SerializedName

// Data class for fetching results and converting them into objects

data class APIResponse (
    @SerializedName (value = "results")
    val listOfBooks: BookList?
)

data class BookList (
    @SerializedName (value = "books")
    val bookList: List<Book>?
)

data class Book (
    @SerializedName (value = "title")
    val title: String?,
    @SerializedName (value = "author")
    val author: String?,
    @SerializedName (value = "book_image")
    val image: String?,
    @SerializedName (value = "weeks_on_list")
    val weeksOnList: Int?,
    @SerializedName (value = "rank")
    val rank: Int?
)