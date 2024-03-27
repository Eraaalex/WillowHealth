package com.example.willowhealth.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthDataSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun register(email: String, phone: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()
    }

    fun logout() {
        auth.signOut()
    }

    fun getName(): String {
        return auth.currentUser?.displayName ?: "User"
    }

}
