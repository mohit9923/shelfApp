package com.example.shelfapp.books

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update




@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    fun getAll(): List<BooksEntity>

    @Query("SELECT * FROM books order by title desc")
    fun getAllByTitle(): List<BooksEntity>

    @Query("SELECT * FROM books order by title asc")
    fun getAllByTitleAsc(): List<BooksEntity>

    @Query("SELECT * FROM books order by hits desc")
    fun getAllByHits(): List<BooksEntity>

    @Query("SELECT * FROM books order by hits asc")
    fun getAllByHitsAsc(): List<BooksEntity>

    @Query("SELECT * FROM books order by favourite desc")
    fun getAllByFavs(): List<BooksEntity>

    @Query("SELECT * FROM books order by favourite asc")
    fun getAllByFavsAsc(): List<BooksEntity>

    @Insert
    fun insertAll(books: List<BooksEntity>)

    @Update
    fun updateAll(iCompanyEntities: List<BooksEntity>)

    @Query("SELECT MAX(timeStamp) from books")
    fun getNewestBookUpdateTime(): Long

    @Query("SELECT COUNT(*) from books")
    fun getBooksCount(): Int

    @Query("DELETE FROM books")
    fun clearAll()

    @Delete
    fun delete(books: BooksEntity)
}