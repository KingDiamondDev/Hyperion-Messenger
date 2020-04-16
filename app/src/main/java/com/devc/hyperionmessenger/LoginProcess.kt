package com.devc.hyperionmessenger
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.log_button
import kotlinx.android.synthetic.main.login_activity.*

class LoginProcess: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        log_button.setOnClickListener {
            val email = log_email.text.toString()
            val password = log_password.text.toString()

            Log.d("Login", "Attempting login with email/pw: $email/***")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and/or password cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener

                        //else if successful
                        Log.d("Main", "Successfully logged user with uid: ${it.result?.user?.uid}")
                        Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    .addOnFailureListener {
                        Log.d("Main", "Could not login user: ${it.message}")
                        Toast.makeText(this, "Couldn't log in", Toast.LENGTH_SHORT).show()
                        return@addOnFailureListener
                    }
            }

            back_reg.setOnClickListener {
                finish()
            }
        }
    }

