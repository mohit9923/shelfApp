package com.example.shelfapp.books

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

open class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @ColumnInfo(name = "type") // Internal type for each entry

    private var type: String? = null

    @ColumnInfo(name = "timeStamp") // When was the last refresh time for a particular opportunity

    private var timeStamp: Long = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getTimeStamp(): Long {
        return timeStamp
    }

    fun setTimeStamp(timeStamp: Long) {
        this.timeStamp = timeStamp
    }
}