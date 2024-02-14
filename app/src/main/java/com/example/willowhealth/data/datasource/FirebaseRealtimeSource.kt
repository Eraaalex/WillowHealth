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

    fun getSurveyData(userId: String) : List<SurveyData> {
        val oneWeekAgo = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        val res = databaseReferenceSurveys.child(userId)
            .orderByChild("timestamp")
            .startAt(oneWeekAgo.toDouble())
            .get()
        val surveyDataList = mutableListOf<SurveyData>()
        res.addOnSuccessListener { it ->
            for (snapshot in it.children) {
                val surveyData = snapshot.getValue(SurveyData::class.java)
                surveyData?.run {
                    surveyDataList.add(this)
                }
            }
        }
        return surveyDataList

    }
}


