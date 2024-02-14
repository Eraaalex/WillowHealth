package com.example.willowhealth.data.datasource

import android.util.Log
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
import com.google.firebase.database.FirebaseDatabase

object FirebaseRealtimeSource {
    private val database = FirebaseDatabase
        .getInstance("https://willowhealth-default-rtdb.europe-west1.firebasedatabase.app")


    private val databaseReferenceUsers = database.getReference("Users")
    private val databaseReferenceSurveys = database.getReference("Surveys")

    fun saveSurveyData(surveyData: SurveyData) {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid

        Log.d(TAG, ("onSaveClick: after determining the user id" + userId))
        userId?.let {
            val surveyId = databaseReferenceSurveys.child(userId)
                .push().key
            databaseReferenceSurveys.child(userId).child(surveyId!!).setValue(surveyData)
                .addOnSuccessListener {
                    Log.d(TAG, "onSaveClick: Successfully saved the survey data")
                }
                .addOnFailureListener {
                    Log.d(TAG, "onSaveClick: Failed to save the survey data")
                }
        }

    }
}


