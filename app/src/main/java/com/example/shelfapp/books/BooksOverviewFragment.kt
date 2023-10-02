package com.example.shelfapp.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.shelfapp.R
import com.example.shelfapp.databinding.FragmentBooksOverviewBinding
import java.text.SimpleDateFormat
import java.util.Date

class BooksOverviewFragment : Fragment() {

    protected lateinit var binding : FragmentBooksOverviewBinding
    private lateinit var closeFragmentListener: CloseFragmentListener
    private var title:String? = null
    private var hits:Int? = null
    private var image:String? = null
    private var favourite:Boolean? = null
    private var alias:String? = null
    private var updatedOn:Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        closeFragmentListener = context as CloseFragmentListener
        closeFragmentListener.backButtonVisibility(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBooksOverviewBinding.inflate(inflater, container, false)
        initviews(binding.root)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
//                    Log.d(TAG, "Fragment back pressed invoked")
                    // Do custom work here

                    // if you want onBackPressed() to be called as normal afterwards
                    DBUtils.ISCOMEFROMOVERVIEW = 1
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
        return binding.root
    }

    fun initviews(view: View?){
        val mArgs = arguments
        if (mArgs != null) {
            title = mArgs.getString("title")
            hits = mArgs.getInt("hits")
            image = mArgs.getString("image")
            favourite = mArgs.getBoolean("favourite")
            alias = mArgs.getString("alias")
            updatedOn = mArgs.getInt("updatedOn")
        }

        binding.bookTitle.text = title
        binding.bookHits.text = hits.toString()
        binding.aliasTxt.text = "Alias: ${alias}"
        val date = SimpleDateFormat("MM/dd/yyyy").format(Date(updatedOn!!.toLong() * 1000))
        binding.updatedOnTxt.text = "Updated on - ${date}"
        if(favourite!!){
            binding.favourite.setImageDrawable(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.baseline_star_24))
        } else {
            binding.favourite.setImageDrawable(ContextCompat.getDrawable(requireActivity().applicationContext,R.drawable.baseline_star_outline_24))
        }
        Glide.with(requireActivity().applicationContext).load(image).into(binding.bookImage)



//        if(!TextUtils.isEmpty(email)) {
//            ApplicationSharedPreference.getInstance()?.saveData("email", email)
//        }
    }
}