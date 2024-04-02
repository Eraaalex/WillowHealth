package com.example.willowhealth.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


interface AuthentificationSource {

    fun getCurrentUser(): FirebaseUser?
    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun register(email: String, phone: String, password: String): Task<AuthResult>

    fun sendVerificationEmail()
    fun logout()
    fun getName(): String

    fun resetPassword(email: String): Task<Void>
}

class FirebaseAuthentificationSourceImpl : AuthentificationSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getCurrentUser() = auth.currentUser

    override fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun register(email: String, phone: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.sendEmailVerification()
    }

    override fun logout() {
        auth.signOut()
    }

    override fun getName(): String {
        return auth.currentUser?.email ?: ""
    }

    override fun resetPassword(email: String): Task<Void> {
        return auth.sendPasswordResetEmail(email)
    }
}
