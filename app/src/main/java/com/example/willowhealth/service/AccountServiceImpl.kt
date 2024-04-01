package com.example.willowhealth.service

import com.example.willowhealth.data.datasource.AuthentificationSource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

interface AccountService {
    suspend fun authenticate(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    fun logout()
    fun getUser(): FirebaseUser?
}


open class AccountServiceImpl(private val authenticationSource: AuthentificationSource) :
    AccountService {

    override suspend fun authenticate(email: String, password: String): Boolean {
        return try {
            authenticationSource.signInWithEmailAndPassword(email, password)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        return try {
            authenticationSource.run {
                register(
                    email,
                    "",
                    password
                ).await()
                sendVerificationEmail()
                signInWithEmailAndPassword(
                    email,
                    password
                ).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun logout() {
        authenticationSource.logout()
    }

    override fun getUser(): FirebaseUser? {
        return authenticationSource.getCurrentUser()
    }

}