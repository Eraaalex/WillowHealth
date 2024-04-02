package com.example.willowhealth.service

import android.app.Activity
import com.example.willowhealth.repository.AccountRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn

interface PermissionService {
    fun checkPermission(): Boolean
    fun requestPermission()
}

class PermissionServiceGoogleImpl(
    private val activity: Activity,
    private val accountRepository: AccountRepository
) : PermissionService {

    override fun checkPermission(): Boolean {
        return GoogleSignIn.hasPermissions(
            accountRepository.get(),
            GoogleFitReader.fitnessOptions
        )
    }

    override fun requestPermission() {
        GoogleSignIn.requestPermissions(
            activity,
            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
            accountRepository.get(),
            GoogleFitReader.fitnessOptions
        )
    }

    companion object {
        const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1
    }
}