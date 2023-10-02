package com.example.shelfapp.books

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BooksModel(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("image")
    @Expose
    var image: String,

    @SerializedName("hits")
    @Expose
    var hits: Int,

    @SerializedName("alias")
    @Expose
    var alias: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("lastChapterDate")
    @Expose
    var lastChapterDate: Int
)
