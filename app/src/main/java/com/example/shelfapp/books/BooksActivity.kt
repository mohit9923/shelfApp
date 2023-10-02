package com.example.shelfapp.books

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shelfapp.R
import com.example.shelfapp.login.LoginActivity
import com.example.shelfapp.sharedpreference.ApplicationSharedPreference
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class BooksActivity : AppCompatActivity(),CloseFragmentListener {
    lateinit var imgBackButton: ImageView
    var email:String? = null
    lateinit var signOut : AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_activity_actual)

        imgBackButton = findViewById(R.id.img_back_button)
        signOut = findViewById(R.id.signOut)

        imgBackButton.setOnClickListener {
            onBackPressed()
        }

        signOut.setOnClickListener {
            signOut("Log out Successful!!")
        }

        email = intent.getStringExtra("email")

        if(!TextUtils.isEmpty(email)) {
            ApplicationSharedPreference.getInstance()?.saveData("email", email)
        }

        var booksList: Fragment = BookListFragment()
        val bundle = Bundle()
        bundle.putString("title",email)
        booksList.arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frame,booksList,"Books Fragment").commit()

//        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // Back is pressed... Finishing the activity
//                if(!TextUtils.isEmpty(ApplicationSharedPreference.getInstance()?.getValueOfSharedPrefrences(this@BooksActivity,"email"))){
//                    finish()
//                }
//            }
//        })
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        super.onBackPressed()
//        if(!TextUtils.isEmpty(ApplicationSharedPreference.getInstance()?.getValueOfSharedPrefrences(this,"email"))){
//            finish()
//        }
//    }

    fun signOut(message: String){
        Firebase.auth.signOut()
        ApplicationSharedPreference.getInstance()?.saveData("email","")
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT,
        ).show()
        DBUtils.dropCompanies()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun closeFragment(title: String, hits: Int, image: String, favourite: Boolean, alias: String, updatedOn: Int) {
        var booksOverview: Fragment = BooksOverviewFragment()
        val bundle = Bundle()
        bundle.putString("title",title)
        bundle.putInt("hits",hits)
        bundle.putString("image",image)
        bundle.putBoolean("favourite",favourite)
        bundle.putString("alias",alias)
        bundle.putInt("updatedOn",updatedOn)
        booksOverview.arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame,booksOverview,"Books Overview Fragment").addToBackStack(null).commit()
    }

    override fun closeActivity() {
        finish()
    }

    override fun backButtonVisibility(visible: Boolean){
        if(visible){
            imgBackButton.visibility = View.VISIBLE
        }else{
            imgBackButton.visibility = View.GONE
        }
    }
}