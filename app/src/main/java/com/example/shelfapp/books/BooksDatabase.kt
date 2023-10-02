package com.example.shelfapp.books

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [BooksEntity::class], version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao


    companion object {


        open fun getNSDDatabase(iContext: Context?): BooksDatabase? {
            return Room.databaseBuilder(
                iContext!!,
                BooksDatabase::class.java, "Database Book Shelf"
            )
                .allowMainThreadQueries()
                .addMigrations(
                    MIGRATION_1_2,
                )
                .build()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

               database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `books` (`booksId` TEXT," +
                            " `image` TEXT, `hits` INTEGER, `alias` TEXT," +
                            " `title` TEXT, `lastChapterDate` INTEGER, `favourite` BOOLEAN, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT, " +
                            "`timeStamp` INTEGER NOT NULL)"
                )
            }
        }
    }
}