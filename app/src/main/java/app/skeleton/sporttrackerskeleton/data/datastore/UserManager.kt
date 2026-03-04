package app.skeleton.sporttrackerskeleton.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.skeleton.sporttrackerskeleton.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.myDataStore by preferencesDataStore(name = "user_prefs")

class UserManager(
    private val context: Context
) {
    suspend fun storeUser(user: UserModel) {
        context.myDataStore.edit { storedUser ->
            storedUser[USER_NAME_KEY] = user.name
            storedUser[USER_HEIGHT_KEY] = user.height
            storedUser[USER_WEIGHT_KEY] = user.weight
            storedUser[USER_AGE_KEY] = user.age
            storedUser[USER_GOAL_WORKOUTS_KEY] = user.goalWorkouts
        }
    }

    suspend fun storeName(name: String) {
        context.myDataStore.edit { it[USER_NAME_KEY] = name }
    }

    suspend fun storeHeight(height: Int) {
        context.myDataStore.edit { it[USER_HEIGHT_KEY] = height }
    }

    suspend fun storeWeight(weight: Float) {
        context.myDataStore.edit { it[USER_WEIGHT_KEY] = weight }
    }

    suspend fun storeAge(age: Int) {
        context.myDataStore.edit { it[USER_AGE_KEY] = age }
    }

    suspend fun storeGoalWorkouts(goal: Int) {
        context.myDataStore.edit { it[USER_GOAL_WORKOUTS_KEY] = goal }
    }

    fun observeUserName(): Flow<String?> {
        return context.myDataStore.data
            .map { preferences ->
                preferences[USER_NAME_KEY]
            }
    }

    fun observeUser(): Flow<UserModel?> {
        return context.myDataStore.data
            .map { storedUser ->
                val name = storedUser[USER_NAME_KEY]
                val height = storedUser[USER_HEIGHT_KEY]
                val weight = storedUser[USER_WEIGHT_KEY]
                val age = storedUser[USER_AGE_KEY]
                val goalWorkouts = storedUser[USER_GOAL_WORKOUTS_KEY]

                val allPresent = listOf(name, height, weight, age, goalWorkouts).all { it != null }

                if (allPresent) {
                    UserModel(
                        name = name!!,
                        height = height!!,
                        weight = weight!!,
                        age = age!!,
                        goalWorkouts = goalWorkouts!!
                    )
                } else {
                    null
                }
            }
    }

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_HEIGHT_KEY = intPreferencesKey("USER_HEIGHT")
        val USER_WEIGHT_KEY = floatPreferencesKey("USER_WEIGHT")
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_GOAL_WORKOUTS_KEY = intPreferencesKey("USER_GOAL_WORKOUTS")
    }
}