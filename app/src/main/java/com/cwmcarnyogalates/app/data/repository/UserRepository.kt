package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<UserModel?>
    fun observeUserName(): Flow<String?>
    suspend fun saveUser(user: UserModel)
}
