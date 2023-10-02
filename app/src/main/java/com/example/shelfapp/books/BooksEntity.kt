package com.example.shelfapp.books

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
class BooksEntity: BaseEntity() {
    @ColumnInfo("booksId")
    var booksId: String? = null

    @ColumnInfo("image")
    var image: String? = null

    @ColumnInfo("hits")
    var hits: Int? = null

    @ColumnInfo("alias")
    var alias: String? = null

    @ColumnInfo("title")
    var title: String? = null

    @ColumnInfo("lastChapterDate")
    var lastChapterDate: Int? = null

    @ColumnInfo("favourite")
    var favourite: Boolean = false
}
