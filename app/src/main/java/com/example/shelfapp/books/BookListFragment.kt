package com.example.shelfapp.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shelfapp.databinding.ActivityBooksBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.lang.reflect.Type


class BookListFragment : Fragment() {


    protected lateinit var binding : ActivityBooksBinding
    var email: String? = null;
    private lateinit var closeFragmentListener: CloseFragmentListener
    var allBooks: MutableList<BooksEntity?>? = ArrayList()
    var sortByTitle = false
    var sortByHits = false
    var sortByFavs = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        closeFragmentListener = context as CloseFragmentListener
        closeFragmentListener.backButtonVisibility(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ActivityBooksBinding.inflate(inflater, container, false)
        initviews(binding.root)
        return binding.root

    }

    fun initviews(view: View?){
        val mArgs = arguments
        if (mArgs != null) {
            email = mArgs.getString("email")
        }

//        if(!TextUtils.isEmpty(email)) {
//            ApplicationSharedPreference.getInstance()?.saveData("email", email)
//        }
        if(DBUtils.shouldRefreshCompanyList()) {
            val listType: Type = object : TypeToken<List<BooksModel?>?>() {}.type
            val gson = Gson()
            var booksData: List<BooksModel> =
                gson.fromJson(loadBooksJson(requireActivity().applicationContext), listType)
            DBUtils.addBooks(booksData);

            val companyEntityList = DBUtils.loadBooks()

            allBooks!!.clear()
            allBooks!!.addAll(companyEntityList!!)
        } else{
            val listOfBooks = DBUtils.loadBooks()
            allBooks!!.clear()
            if (listOfBooks != null) {
                allBooks!!.addAll(listOfBooks)
            }
        }

        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity)
        var adapter = activity?.let {
            BooksAdapter(allBooks!!,closeFragmentListener,
                it.applicationContext)
        }
        binding.booksRecyclerView.layoutManager = layoutManager
        binding.booksRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.booksRecyclerView.adapter = adapter

//        binding.chipgroup.setOnCheckedChangeListener { chipGroup, i ->
//            val chip = chipGroup.findViewById<Chip>(i)
//            if (chip != null) Toast.makeText(
//                requireActivity().getApplicationContext(),
//                "Chip is " + chip.text,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        if(binding.chipTitle.isChecked){
//            binding.switch1.isEnabled = true
//            val listOfBooks = DBUtils.loadBooksByTitle()
//            allBooks!!.clear()
//            if (listOfBooks != null) {
//                allBooks!!.addAll(listOfBooks)
//            }
//            adapter = activity?.let {
//                BooksAdapter(allBooks!!,closeFragmentListener,
//                    it.applicationContext)
//            }
//            binding.booksRecyclerView.adapter = adapter
//            adapter?.setBooksDataList()
//        }
//        if(binding.chipHits.isChecked){
//            binding.switch1.isEnabled = true
//            val listOfBooks = DBUtils.loadBooksByHits()
//            allBooks!!.clear()
//            if (listOfBooks != null) {
//                allBooks!!.addAll(listOfBooks)
//            }
//            adapter = activity?.let {
//                BooksAdapter(allBooks!!,closeFragmentListener,
//                    it.applicationContext)
//            }
//            binding.booksRecyclerView.adapter = adapter
//            adapter?.setBooksDataList()
//        }
//        if(binding.chipFavs.isChecked){
//            binding.switch1.isEnabled = true
//            val listOfBooks = DBUtils.loadBooksByFavs()
//            allBooks!!.clear()
//            if (listOfBooks != null) {
//                allBooks!!.addAll(listOfBooks)
//            }
//            adapter = activity?.let {
//                BooksAdapter(allBooks!!,closeFragmentListener,
//                    it.applicationContext)
//            }
//            binding.booksRecyclerView.adapter = adapter
//            adapter?.setBooksDataList()
//        }

        binding.chipTitle.setOnClickListener {
            if(binding.chipTitle.isChecked){
                binding.switch1.isEnabled = true
                val listOfBooks = DBUtils.loadBooksByTitle()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }else{
                binding.switch1.isEnabled = false
                val listOfBooks = DBUtils.loadBooks()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }
        }

        binding.chipHits.setOnClickListener {
            if(binding.chipHits.isChecked){
                binding.switch1.isEnabled = true
                val listOfBooks = DBUtils.loadBooksByHits()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }else{
                binding.switch1.isEnabled = false
                val listOfBooks = DBUtils.loadBooks()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }
        }

        binding.chipFavs.setOnClickListener {
            if(binding.chipFavs.isChecked){
                binding.switch1.isEnabled = true
                val listOfBooks = DBUtils.loadBooksByFavs()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }else{
                binding.switch1.isEnabled = false
                val listOfBooks = DBUtils.loadBooks()
                allBooks!!.clear()
                if (listOfBooks != null) {
                    allBooks!!.addAll(listOfBooks)
                }
                adapter = activity?.let {
                    BooksAdapter(allBooks!!,closeFragmentListener,
                        it.applicationContext)
                }
                binding.booksRecyclerView.adapter = adapter
                adapter?.setBooksDataList()
            }
        }

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if(binding.chipTitle.isChecked){
                    val listOfBooks = DBUtils.loadBooksByTitleAsc()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
                if(binding.chipHits.isChecked){
                    val listOfBooks = DBUtils.loadBooksByHitsAsc()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
                if(binding.chipFavs.isChecked){
                    val listOfBooks = DBUtils.loadBooksByFavsAsc()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
            } else {
                if(binding.chipTitle.isChecked){
                    val listOfBooks = DBUtils.loadBooksByTitle()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
                if(binding.chipHits.isChecked){
                    val listOfBooks = DBUtils.loadBooksByHits()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
                if(binding.chipFavs.isChecked){
                    val listOfBooks = DBUtils.loadBooksByFavs()
                    allBooks!!.clear()
                    if (listOfBooks != null) {
                        allBooks!!.addAll(listOfBooks)
                    }
                    adapter = activity?.let {
                        BooksAdapter(allBooks!!,closeFragmentListener,
                            it.applicationContext)
                    }
                    binding.booksRecyclerView.adapter = adapter
                    adapter?.setBooksDataList()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        closeFragmentListener.backButtonVisibility(false)
        if(DBUtils.ISCOMEFROMOVERVIEW == 1){
            DBUtils.ISCOMEFROMOVERVIEW = 0
            binding.chipTitle.isChecked = false
            binding.chipHits.isChecked = false
            binding.chipFavs.isChecked = false
        }

    }

    private fun loadBooksJson(context: Context): String?{
        var input: InputStream? = null
        var jsonString: String

        try{
            input = context.assets.open("books.json")
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            jsonString = String(buffer)
            return jsonString
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            input?.close()
        }

        return null
    }

}