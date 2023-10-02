package com.example.shelfapp.application

import android.app.Application
import com.example.shelfapp.books.BooksDatabase

class BookShelfApplication: Application() {

    companion object{
        private var mInstance: BookShelfApplication? = null
        private var sBooksDatabase: BooksDatabase? = null

        @Synchronized
        fun getInstance(): BookShelfApplication? {
            return mInstance
        }

        fun getNSDDatabase(): BooksDatabase? {
            return sBooksDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        // Initialize the database
        // Note CCR Database initialisation is done
        sBooksDatabase = BooksDatabase.getNSDDatabase(this)
    }
}