package com.example.shelfapp.books

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shelfapp.R
import com.example.shelfapp.databinding.BooksListCardViewBinding

class BooksAdapter(bookList: MutableList<BooksEntity?>, closeFragmentListener: CloseFragmentListener,context: Context): RecyclerView.Adapter<BooksAdapter.BaseViewHolder>(){

    private lateinit var binding : BooksListCardViewBinding
    private var booksList: MutableList<BooksEntity?>
    private var closeFragmentListener: CloseFragmentListener
    private var context: Context

    init {
        this.booksList = bookList
        this.closeFragmentListener = closeFragmentListener
        this.context = context
    }

//    @Synchronized
//    fun setBooksDataList(booksDataList: MutableList<BooksEntity?>?) {
//// this.mCompanyList = companyDataList;
//        booksList.clear()
//        notifyDataSetChanged()
//        if (booksDataList != null) {
//            booksList.addAll(booksDataList)
//        }
//        notifyDataSetChanged()
//    }

    @Synchronized
    fun setBooksDataList() {
// this.mCompanyList = companyDataList;

        notifyDataSetChanged()
    }

    inner class BaseViewHolder(val binding: BooksListCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val id = adapterPosition
                if (id > -1) {
                    try {
                        val booksEntity: BooksEntity? = booksList.get(id)
                        val title = booksEntity?.title
                        val hits = booksEntity?.hits
                        val image = booksEntity?.image
                        val favourite = booksEntity?.favourite
                        val alias = booksEntity?.alias
                        val updatedOn = booksEntity?.lastChapterDate
                        closeFragmentListener.closeFragment(title!!,hits!!,image!!,favourite!!,alias!!,updatedOn!!)
                    } catch (ignored: Exception) {
                    }
                }
            }
            binding.favourite.setOnClickListener {
                if(booksList[adapterPosition]?.favourite == true){
                    booksList[adapterPosition]?.favourite = false
                    DBUtils.updateDatabase(booksList as List<BooksEntity>)
                    binding.favourite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.baseline_star_outline_24))
                }else{
                    booksList[adapterPosition]?.favourite = true
                    DBUtils.updateDatabase(booksList as List<BooksEntity>)
                    binding.favourite.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.baseline_star_24))
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        binding = BooksListCardViewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.bookTitle.text = booksList[position]?.title
        holder.binding.bookHits.text = booksList[position]?.hits.toString()
        holder.binding.favourite.setImageDrawable(
            if(booksList[position]!!.favourite){
                ContextCompat.getDrawable(context,R.drawable.baseline_star_24)
            }else{
                ContextCompat.getDrawable(context,R.drawable.baseline_star_outline_24)
            }
        )
        Glide.with(context).load(booksList[position]?.image).into(holder.binding.bookImage)
    }

}