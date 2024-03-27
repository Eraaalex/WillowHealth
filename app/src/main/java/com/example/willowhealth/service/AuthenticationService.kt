package com.example.willowhealth.service

import com.example.willowhealth.data.datasource.FirebaseAuthDataSource
import kotlinx.coroutines.tasks.await

open class AuthenticationService() {

    suspend fun signInUser(email: String, password: String): Boolean {
        return try {
            FirebaseAuthDataSource.signInWithEmailAndPassword(email, password)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun signUpUser(email: String, password: String): Boolean {
        return try {
            FirebaseAuthDataSource.run {
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


}