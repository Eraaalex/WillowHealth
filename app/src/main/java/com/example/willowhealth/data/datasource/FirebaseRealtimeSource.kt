package com.example.willowhealth.data.datasource

import android.util.Log
import com.example.willowhealth.main.TAG
import com.example.willowhealth.model.MissionData
import com.example.willowhealth.model.SurveyData
import com.example.willowhealth.model.UserData
import com.example.willowhealth.utils.getCurrentDayOfWeek
import com.example.willowhealth.utils.toGPTRequestForm
import com.example.willowhealth.utils.toLocalDate
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await
import java.time.DayOfWeek
import java.util.concurrent.TimeUnit

object FirebaseRealtimeSource {
    private val database = FirebaseDatabase
        .getInstance("https://willowhealth-default-rtdb.europe-west1.firebasedatabase.app")

    private val databaseReferenceSurvey = database.getReference("Surveys")
    private val databaseReferenceMission = database.getReference("Missions")

    fun saveSurveyData(userData: UserData) {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid
        val databaseReference = databaseReferenceSurvey
        userId?.let {
            val userDataId = databaseReference.child(userId)
                .push().key
            databaseReference.child(userId).child(userDataId!!).setValue(userData)
                .addOnSuccessListener {
                    Log.d(TAG, "[onSaveClick:] Successfully saved the survey data")
                }
                .addOnFailureListener {
                    Log.d(TAG, "[onSaveClick:] Failed to save the survey data")
                }
        }
    }

    suspend fun fetchMissionsForWeek(week: Int): List<MissionData> {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""
        val databaseReference = databaseReferenceMission

        val snapshot = databaseReference.child(userId).child("Week" + week).get().await()
        val missions = mutableListOf<MissionData>()

        for (childSnapshot in snapshot.children) {
            val mission = childSnapshot.getValue<MissionData>()
            mission?.let { missions.add(it) }
        }

        return missions
    }

    suspend fun updateMission(mission: MissionData) {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""
        val updateMap = mapOf("isChecked" to mission.isChecked)
        databaseReferenceMission
            .child(userId)
            .child("week" + 1) // TODO week checking
            .child("mission" + mission.number)
            .updateChildren(updateMap).await()

    }


    private suspend fun fetchSurveyData(
        userId: String,
        startTime: Long = 7 * 24 * 60 * 60 * 1000
    ): List<SurveyData> {
        val oneWeekAgo = System.currentTimeMillis() - startTime
        val res = databaseReferenceSurvey.child(userId)
            .orderByChild("timestamp")
            .startAt(oneWeekAgo.toDouble())
            .get().await()
        val surveyDataList = mutableListOf<SurveyData>()
        res.let { it ->
            for (snapshot in it.children) {
                val surveyData = snapshot.getValue(SurveyData::class.java)
                surveyData?.run {
                    surveyDataList.add(this)
                }
            }
        }
        return surveyDataList
    }

    fun addMissionForUser(userId: String, week: Int, mission: MissionData) {
        val missionRef = database.getReference("users/$userId/Missions/Week$week").push()
        missionRef.setValue(mission)
    }

    fun addSurveyForUser(userId: String, survey: SurveyData) {
        val surveyRef = database.getReference("users/$userId/Surveys").push()
        surveyRef.setValue(survey)
    }

    suspend fun getMissions(userId: String = FirebaseAuthDataSource.getCurrentUser()?.uid ?: "") {
        val res = database.getReference("Missions").child(userId).get().await()
        Log.e(TAG, "getMissions: $res")
    }

    fun addMissionToWeek(userId: String, week: String, mission: MissionData) {
        val missionRef = database.getReference("users/$userId/$week")
        val newMissionRef = missionRef.push() // Creates a new child with a unique key
        newMissionRef.setValue(mission)
    }

    suspend fun getSurveyDataString(): String {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""
        val surveyData = fetchSurveyData(userId)
        Log.d(TAG, surveyData.toString())
        return surveyData.joinToString("\n") {
            it.toGPTRequestForm()
        }
    }

    suspend fun getSurveyDataUntilCurrent(
        days: Long = 1
    ): HashMap<DayOfWeek, Int> {
        val userId = FirebaseAuthDataSource.getCurrentUser()?.uid ?: ""
        val daysAgo = System.currentTimeMillis() - (days) * TimeUnit.DAYS.toMillis(1)
        Log.d(TAG, "getSurveyDataUntilCurrent " + daysAgo)
        val currentDayOfWeek = getCurrentDayOfWeek()
        val res = databaseReferenceSurvey.child(userId)
            .orderByChild("timestamp")
            .startAt(daysAgo.toDouble())
            .get().await()

        val surveyDataMap = HashMap<DayOfWeek, Int>()
        Log.e(TAG, "3 " + res)
        res.let { it ->
            for (snapshot in it.children) {
                val surveyData = snapshot.getValue(SurveyData::class.java)
                surveyData?.let {
                    val dayOfWeek = it.timestamp.toLocalDate().dayOfWeek
                    if (dayOfWeek <= currentDayOfWeek) {
                        surveyDataMap.put(
                            it.timestamp.toLocalDate().dayOfWeek,
                            (-it.startSleepTime + it.endSleepTime +
                                    if (it.startSleepTime > it.endSleepTime)
                                        0
                                    else
                                        0
                                    )
                        )
                    }


                }
            }
        }
        return surveyDataMap

    }


}


