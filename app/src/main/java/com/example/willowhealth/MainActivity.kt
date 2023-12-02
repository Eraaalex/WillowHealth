package com.example.willowhealth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.example.willowhealth.GoogleFitReader
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

//import com.samsung.android.sdk.healthdata.HealthDataStore

class MainActivity : AppCompatActivity() {


    private lateinit var googleFitReader : GoogleFitReader
   // private lateinit var healthDataManager: HealthDataManager
    private lateinit var stepsTextView: TextView
    var GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stepsTextView = findViewById(R.id.steps_tv)
     //   healthDataManager = HealthDataManager()
        googleFitReader = GoogleFitReader()
        setupGoogleFit()
    }


    private fun setupGoogleFit() {
        val account = GoogleSignIn.getAccountForExtension(this, GoogleFitReader.fitnessOptions)
        if (!GoogleSignIn.hasPermissions(account, GoogleFitReader.fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g. 1
                account,
                GoogleFitReader.fitnessOptions)
        } else {
             displaySteps()
        }
    }

    private fun displaySteps() {
        googleFitReader.getSteps(this) { steps ->
            runOnUiThread {
                stepsTextView.text = steps.toString()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE -> displaySteps()
                else -> {
                    // Result wasn't from Google Fit
                }
            }
            else -> {
                // Permission not granted
            }
        }
    }


}