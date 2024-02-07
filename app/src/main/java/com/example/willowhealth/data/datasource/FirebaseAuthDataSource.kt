package com.example.willowhealth.data.datasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthDataSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun register(email: String, phone: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = auth.currentUser
            if (user != null) {
                Log.d("MyApp", "Authorized")
            } else {
                Log.d("MyApp", " Not Authorized")
            }
            sendVerificationEmail()
        }.addOnFailureListener {
            Log.d("MyApp", it.message ?: "Oh, it failed")
        }
    }

    fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {

                }
            }
    }

    fun logout() {
        auth.signOut()
        
    }


}
