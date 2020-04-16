package com.devc.hyperionmessenger

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val anime = mainpage.background as AnimationDrawable
        anime.setEnterFadeDuration(10)
        anime.setExitFadeDuration(4000)
        anime.start()

        reg_button.setOnClickListener {
            registerUFirebase()
        }

        log_button.setOnClickListener {
            //watch the login
            Log.d("MainActivity", "Try to show login activity")

            //launch login
            val intent = Intent(this, LoginProcess::class.java)
            startActivity(intent)
        }

        photo_button.setOnClickListener {

        }
    }
    private fun registerUFirebase() {
        val email = email_reg.text.toString()
        val password = password_reg.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and/or password cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("MainActivity", "Email is: " + email)
        Log.d("MainActivity", "Password: $password")

        //Authenticate with Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "User successfully created!", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Couldn't create your user. ${it.message}", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
    }
}
