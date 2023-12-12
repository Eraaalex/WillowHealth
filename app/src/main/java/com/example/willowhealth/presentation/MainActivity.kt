package com.example.willowhealth.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.willowhealth.HealthMetrics
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.willowhealth.R
import com.example.willowhealth.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//import com.samsung.android.sdk.healthdata.HealthDataStore

class MainActivity : AppCompatActivity() {


    private val viewModel  by viewModel<MainViewModel>()
    private lateinit var stepsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        viewModel.steps.observe(this, Observer {stepsData->
            stepsTextView.text = stepsData.toString()
        })
        viewModel.fetchData(HealthMetrics.STEPS)
    }
    private fun initViews(){
        stepsTextView = findViewById(R.id.steps_tv)
    }



}