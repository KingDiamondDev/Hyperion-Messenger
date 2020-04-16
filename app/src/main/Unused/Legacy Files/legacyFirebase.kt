package com.devc.hyperionmessenger

/* import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class registerUFirebase: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            val email = email_reg.text.toString()
            val password = password_reg.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and/or password cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
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
                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to create user: ${it.message}")
                    Toast.makeText(
                        this,
                        "Couldn't create your user. Maybe check your connection?",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addOnFailureListener
                }
        }
    }
    */