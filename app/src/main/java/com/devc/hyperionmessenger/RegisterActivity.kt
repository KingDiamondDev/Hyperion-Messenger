package com.devc.hyperionmessenger

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class RegisterActivity : AppCompatActivity() {

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
            Log.d("RegisterActivity", "Try to show login activity")

            //launch login
            val intent = Intent(this, LoginProcess::class.java)
            startActivity(intent)
        }

        photo_button.setOnClickListener {
            Log.d("RegisterPhoto", "Try to show gallery selector...")

            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }
    }

    var photoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed to check image
            Log.d("RegisterPhoto", "Photo was selected")

            photoUri = data.data

            //temporary fix
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            photoview.setImageBitmap(bitmap)
            photo_button.alpha = 0f


            //disregard
          /*  val bitmapDrawable = BitmapDrawable(bitmap)
            photo_button.setBackgroundDrawable(bitmapDrawable)
          */

            //implement that in future update
          /*  if (Build.VERSION.SDK_INT < 28){
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
                val bitmapDrawable = BitmapDrawable(bitmap)
                photo_button.setBackgroundDrawable()
            } else if(Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(this.contentResolver, photoUri!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                photo_button.setBackgroundDrawable(bitmap)
            }
            */

        }
    }
    private fun registerUFirebase() {
        val email = email_reg.text.toString()
        val password = password_reg.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and/or password cannot be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity", "Email is: " + email)
        Log.d("RegisterActivity", "Password: $password")

        //Authenticate with Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
                uploadImageToFirebase()
                Toast.makeText(this, "User successfully created!", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Couldn't create your user. ${it.message}", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
    }

    private fun uploadImageToFirebase() {
        if (photoUri == null) return

        val filename = UUID.randomUUID().toString()
        val uPhoto = FirebaseStorage.getInstance().getReference("/images/$filename")
        uPhoto.putFile(photoUri!!)
            .addOnSuccessListener {
                Log.d("PhotoUpload", "Successfully uploaded photo to Firebase: ${it.metadata?.path}")
                 uPhoto.downloadUrl.addOnSuccessListener {
                     it.toString()
                     Log.d("PhotoUpload", "File Location: $it")

                     saveUserToFirebase(it.toString())
                 }
            }
    }

    private fun saveUserToFirebase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val uuidUser = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username_reg.text.toString(), profileImageUrl)

        uuidUser.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "User added to Firebase Database.")

                val intent = Intent(this, MessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}

class User(val uid: String, val username: String, val profileImageUrl: String)