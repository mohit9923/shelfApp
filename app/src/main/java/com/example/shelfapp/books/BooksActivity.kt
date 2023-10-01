package com.example.shelfapp.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.shelfapp.R
import com.example.shelfapp.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BooksActivity : AppCompatActivity() {
    lateinit var signout: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        signout = findViewById(R.id.signOut)

        signout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(
                baseContext,
                "Log out Successful!!",
                Toast.LENGTH_SHORT,
            ).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}