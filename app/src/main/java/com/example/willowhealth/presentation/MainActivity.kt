package com.example.willowhealth.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.willowhealth.service.GoogleFitReader
import com.example.willowhealth.HealthMetric
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.willowhealth.R
import com.example.willowhealth.app.WillowHealth
import com.example.willowhealth.presentation.main.di.getMainModule
import com.google.android.gms.auth.api.signin.GoogleSignIn

//import com.samsung.android.sdk.healthdata.HealthDataStore

class MainActivity : AppCompatActivity() {


    private val viewModel  by viewModel<MainViewModel>()
    private lateinit var stepsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val module = getMainModule(this)
        (applicationContext as WillowHealth).koinApp.modules(module) // integration
        initViews()
        viewModel.steps.observe(this, Observer {stepsData->
            stepsTextView.text = stepsData.toString()
        })
        viewModel.fetchData(HealthMetric.STEPS)

    }

    private fun initViews(){
        stepsTextView = findViewById(R.id.steps_tv)
    }
}