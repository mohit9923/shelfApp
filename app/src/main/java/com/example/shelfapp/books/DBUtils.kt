package com.example.shelfapp.books

import com.example.shelfapp.application.BookShelfApplication
import java.util.Calendar

class DBUtils {

    companion object {

        var booksDatabase: BooksDatabase? = BookShelfApplication.getNSDDatabase()
        private val mBooksDao: BooksDao? = booksDatabase?.booksDao()
        var ISCOMEFROMOVERVIEW = 0
        fun addBooks(iBooksModelList: List<BooksModel?>?) {
            if (iBooksModelList == null || iBooksModelList.isEmpty()) return

            // Create entities based on the data received from api response
            val booksEntityList: MutableList<BooksEntity> = ArrayList<BooksEntity>()
            val currentTimeStamp = System.currentTimeMillis()
            for (booksModel in iBooksModelList) {
                // Create and adding  main company specific data
                val booksEntity = BooksEntity()
                booksEntity.booksId = booksModel?.id
                booksEntity.hits = booksModel?.hits
                booksEntity.image = booksModel?.image
                booksEntity.alias = booksModel?.alias
                booksEntity.title = booksModel?.title
                booksEntity.lastChapterDate = booksModel?.lastChapterDate
                booksEntity.favourite = false
                booksEntity.setType("books")
                booksEntity.setTimeStamp(currentTimeStamp)
                booksEntityList.add(booksEntity)
            }
            mBooksDao?.insertAll(booksEntityList)
        }

        fun shouldRefreshCompanyList(): Boolean {
            // Check if refresh is required or not
            var mostRecentOpportunityTime: Long = mBooksDao!!.getNewestBookUpdateTime();
            var companyCount:Int = mBooksDao.getBooksCount();
            return companyCount <= 0 || hasDayChanged(mostRecentOpportunityTime);
        }

        fun loadBooks(): List<BooksEntity?>? {
            return mBooksDao?.getAll()
        }

        fun loadBooksByTitle(): List<BooksEntity?>? {
            return mBooksDao?.getAllByTitle()
        }

        fun loadBooksByTitleAsc(): List<BooksEntity?>? {
            return mBooksDao?.getAllByTitleAsc()
        }

        fun loadBooksByHits(): List<BooksEntity?>? {
            return mBooksDao?.getAllByHits()
        }

        fun loadBooksByHitsAsc(): List<BooksEntity?>? {
            return mBooksDao?.getAllByHitsAsc()
        }

        fun loadBooksByFavs(): List<BooksEntity?>? {
            return mBooksDao?.getAllByFavs()
        }

        fun loadBooksByFavsAsc(): List<BooksEntity?>? {
            return mBooksDao?.getAllByFavsAsc()
        }

        fun updateDatabase(booksEntity: List<BooksEntity>){
            mBooksDao?.updateAll(booksEntity)
        }

        fun dropCompanies() {
            mBooksDao?.clearAll()
        }

        fun hasDayChanged(iLastTimeStamp: Long): Boolean {
            if (iLastTimeStamp == 0L || iLastTimeStamp == -1L) return true
            val currentTime = System.currentTimeMillis()
            val lastOpportunityCalendar = Calendar.getInstance()
            lastOpportunityCalendar.timeInMillis = iLastTimeStamp
            val currentCalendar = Calendar.getInstance()
            currentCalendar.timeInMillis = currentTime

            // Check if day is different
            val lastDay = lastOpportunityCalendar[Calendar.DAY_OF_MONTH]
            val currDay = currentCalendar[Calendar.DAY_OF_MONTH]
            return Math.abs(currDay - lastDay) > 0
        }


    }

}