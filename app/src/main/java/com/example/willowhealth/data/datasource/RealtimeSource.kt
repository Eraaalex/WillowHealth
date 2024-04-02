package com.example.willowhealth.data.datasource

import android.util.Log
import com.example.willowhealth.model.FirebaseData
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.presentation.main.TAG
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.coroutines.tasks.await


interface RealtimeSource {
    suspend fun fetchMissionData(week: Int = 1): List<MissionData>
    suspend fun fetchSurveyData(days: Int = 7): List<SurveyData>
    fun saveSurveyData(userData: SurveyData)
    suspend fun updateMission(week: Int, mission: MissionData)
    fun getUserId(): String
}

class FirebaseRealtimeSourceImpl(private val authDataSource: AuthentificationSource) :
    RealtimeSource {
    private val database = FirebaseDatabase
        .getInstance("https://willowhealth-default-rtdb.europe-west1.firebasedatabase.app")

    private val databaseReferenceSurvey = database.getReference("Surveys")
    private val databaseReferenceMission = database.getReference("Missions")

    private suspend fun <T : FirebaseData> fetchData(
        days: Int = 1,
        dataClass: Class<T>,
        reference: DatabaseReference,
        queryModifier: (DatabaseReference) -> Query
    ): List<T> {
        val userId = authDataSource.getCurrentUser()?.uid ?: ""
        val snapshot = queryModifier(reference.child(userId))
            .get().await()
        val dataList = mutableListOf<T>()
        snapshot.let { it ->
            for (snapshot in it.children) {
                val data = snapshot.getValue(dataClass)
                data?.run {
                    dataList.add(this)
                }
            }
        }
        return dataList
    }

    override fun getUserId(): String = authDataSource.getCurrentUser()?.uid ?: ""

    override suspend fun fetchSurveyData(days: Int): List<SurveyData> {
        val period = System.currentTimeMillis() - days * 24 * 3600 * 1000
        return fetchData(days, SurveyData::class.java, databaseReferenceSurvey) {
            it.orderByChild("timestamp")
                .startAt(period.toDouble())

        }
    }

    override suspend fun fetchMissionData(week: Int): List<MissionData> {
        return fetchData(week, MissionData::class.java, databaseReferenceMission) {
            it.child("Week" + week)
        }
    }

    override fun saveSurveyData(userData: SurveyData) {
        val userId = authDataSource.getCurrentUser()?.uid ?: ""
        userId.let {
            val userDataId = databaseReferenceSurvey.child(userId)
                .push().key
            databaseReferenceSurvey.child(userId).child(userDataId!!).setValue(userData)
                .addOnSuccessListener {
                    Log.d(TAG, "[onSaveClick:] Successfully saved the survey data")
                }
                .addOnFailureListener {
                    Log.d(TAG, "[onSaveClick:] Failed to save the survey data")
                }
        }
    }

    override suspend fun updateMission(week: Int, mission: MissionData) {
        val userId = authDataSource.getCurrentUser()?.uid ?: ""
        val updateMap = mapOf("isChecked" to mission.isChecked)
        databaseReferenceMission
            .child(userId)
            .child("Week" + week)
            .child("mission" + mission.number)
            .updateChildren(updateMap).await()
    }

    fun addMissionForUser(userId: String, week: Int, mission: MissionData) {
        val missionRef = database.getReference("users/$userId/Missions/Week$week").push()
        missionRef.setValue(mission)
    }

    fun addSurveyForUser(userId: String, survey: SurveyData) {
        val surveyRef = database.getReference("users/$userId/Surveys").push()
        surveyRef.setValue(survey)
    }

    suspend fun getMissions(userId: String = authDataSource.getCurrentUser()?.uid ?: "") {
        val res = database.getReference("Missions").child(userId).get().await()
        Log.e(TAG, "getMissions: $res")
    }

    fun addMissionToWeek(userId: String, week: String, mission: MissionData) {
        val missionRef = database.getReference("users/$userId/$week")
        val newMissionRef = missionRef.push()
        newMissionRef.setValue(mission)
    }


}


