package com.example.shelfapp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.shelfapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    lateinit var signupButton: AppCompatButton;
    lateinit var usernametext: TextView;
    lateinit var passwordText: TextView;
    lateinit var progressBar: ProgressBar;
    lateinit var selectedCountry: String;
    lateinit var passwordInvalidText: TextView
    lateinit var emailInvalidText: TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupButton = findViewById(R.id.SignUpButton)
        usernametext = findViewById(R.id.username_edit_text)
        passwordText = findViewById(R.id.password_edit_text)
        passwordInvalidText = findViewById(R.id.passWordInvalid)
        emailInvalidText = findViewById(R.id.emailInvalid)
        progressBar = findViewById(R.id.progressBar)
        auth = Firebase.auth
        val items: MutableList<String> = ArrayList()
        try {
            val jsonArray = JSONArray(loadCountriesJson(this))
            for (i in 0 until jsonArray.length()) {
                val item: String = jsonArray.getString(i)
                items.add(item)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val spinner: Spinner = findViewById(R.id.countrypinner)
        spinner.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items))

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                selectedCountry = parentView.getItemAtPosition(position) as String
                // Perform actions based on the selected item

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing here
            }
        }

        signupButton.setOnClickListener {
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
            if(isValidEmail(email)){
                if(isValidPassword(password)) {
                    progressBar.visibility= View.VISIBLE
                    passwordInvalidText.visibility= View.GONE
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity()) { task ->
                            progressBar.visibility= View.GONE
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                //                        Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                Log.d(
                                    "user data is",
                                    user?.email.toString() + " - " + user?.uid.toString()
                                )
                                Toast.makeText(
                                    baseContext,
                                    "User Registered Successfully!!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                startActivity(Intent(this,LoginActivity::class.java))
                                //                        updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                //                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "${task.exception?.message.toString()}",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                //                        updateUI(null)
                            }
                        }
                } else{
                    passwordInvalidText.visibility= View.VISIBLE
                    Toast.makeText(this,"Please ensure that the password consists of min 8 characters with\n" +
                            "atleast one number, special characters[!@#\$%&()], one lowercase letter, and one\n" +
                            "uppercase letter",Toast.LENGTH_SHORT).show()
                }
            } else{
                emailInvalidText.visibility = View.VISIBLE
            }

        }
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%&()]).{8,}\$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isValidEmail(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})\$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun loadCountriesJson(context:Context): String?{
        var input: InputStream? = null
        var jsonString: String

        try{
            input = context.assets.open("countries.json")
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