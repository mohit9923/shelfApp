package com.example.shelfapp.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.shelfapp.R
import com.example.shelfapp.books.BooksActivity
import com.example.shelfapp.books.DBUtils
import com.example.shelfapp.sharedpreference.ApplicationSharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(){
    lateinit var signupButton: TextView;
    lateinit var loginButton: AppCompatButton;
    lateinit var progressBar: ProgressBar;
    lateinit var usernametext: TextView;
    lateinit var passwordText: TextView;
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        if(!TextUtils.isEmpty(ApplicationSharedPreference.getInstance()?.getValueOfSharedPrefrences(this,"email"))){
            if(DBUtils.shouldRefreshCompanyList()){
                DBUtils.dropCompanies()
            }else {
                startActivity(Intent(this, BooksActivity::class.java))
                this.finishAffinity()
            }
        }

        signupButton = findViewById(R.id.signupText)
        progressBar = findViewById(R.id.progressBar)
        loginButton = findViewById(R.id.LoginButton)
        usernametext = findViewById(R.id.username_edit_text)
        passwordText = findViewById(R.id.password_edit_text)
        auth = Firebase.auth

        signupButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity()::class.java))
        }

        loginButton.setOnClickListener {
            val email = usernametext.text.toString()
            val password = passwordText.text.toString()
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(
                    baseContext,
                    "Email or password cannot be empty!",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }
            progressBar.visibility= View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Login SuccessFul!!",
                            Toast.LENGTH_SHORT,
                        ).show()
                        var intent = Intent(this,BooksActivity::class.java)
                        intent.putExtra("email",email)
                        startActivity(intent)
                        this.finishAffinity()
//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "${task.exception?.message.toString()}",
                            Toast.LENGTH_SHORT,
                        ).show()
//                        updateUI(null)
                    }
                }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}