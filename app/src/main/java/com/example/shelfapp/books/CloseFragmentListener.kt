package com.example.shelfapp.books

interface CloseFragmentListener {

    fun closeFragment(title:String,hits:Int,image:String, favourite: Boolean, alias: String, updatedOn: Int)

    fun closeActivity()

    fun backButtonVisibility(visible: Boolean)

}