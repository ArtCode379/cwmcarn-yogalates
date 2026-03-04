package app.skeleton.sporttrackerskeleton.data.repository

import app.skeleton.sporttrackerskeleton.data.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun observeUser(): Flow<UserModel?>

    fun observeUserName(): Flow<String?>

    suspend fun saveUser(user: UserModel)
}