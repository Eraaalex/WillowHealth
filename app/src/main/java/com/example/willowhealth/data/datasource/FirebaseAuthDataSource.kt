package com.example.willowhealth.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthDataSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getCurrentUser() = auth.currentUser

    fun register(email: String, phone: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = auth.currentUser
            sendVerificationEmail()
        }.addOnFailureListener {
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

    fun getName(): String {
        return auth.currentUser?.email?.substringBefore("@")?.replaceFirstChar { char -> char - 32 }
            ?: ""
    }


}
