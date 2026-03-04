package com.cwmcarnyogalates.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cwmcarnyogalates.app.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserManager(private val context: Context) {

    suspend fun storeUser(user: UserModel) {
        context.userDataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = user.name
            prefs[USER_EMAIL_KEY] = user.email
            prefs[USER_HEIGHT_KEY] = user.height
            prefs[USER_WEIGHT_KEY] = user.weight
            prefs[USER_AGE_KEY] = user.age
            prefs[USER_GOAL_WORKOUTS_KEY] = user.goalWorkouts
        }
    }

    fun observeUserName(): Flow<String?> =
        context.userDataStore.data.map { it[USER_NAME_KEY] }

    fun observeUser(): Flow<UserModel?> =
        context.userDataStore.data.map { prefs ->
            val name = prefs[USER_NAME_KEY] ?: return@map null
            UserModel(
                name = name,
                email = prefs[USER_EMAIL_KEY] ?: "",
                height = prefs[USER_HEIGHT_KEY] ?: 0,
                weight = prefs[USER_WEIGHT_KEY] ?: 0f,
                age = prefs[USER_AGE_KEY] ?: 0,
                goalWorkouts = prefs[USER_GOAL_WORKOUTS_KEY] ?: 0,
            )
        }

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL")
        val USER_HEIGHT_KEY = intPreferencesKey("USER_HEIGHT")
        val USER_WEIGHT_KEY = floatPreferencesKey("USER_WEIGHT")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_GOAL_WORKOUTS_KEY = intPreferencesKey("USER_GOAL_WORKOUTS")
    }
}
