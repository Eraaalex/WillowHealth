package com.example.willowhealth.repository

import android.content.Context
import com.example.willowhealth.service.GoogleFitReader
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AccountRepository {
    fun get(): GoogleSignInAccount
}

class AccountRepositoryImpl(
    private val context: Context,
) : AccountRepository {
    override fun get(): GoogleSignInAccount {
        return GoogleSignIn.getAccountForExtension(
            context,
            GoogleFitReader.fitnessOptions
        )
    }
}