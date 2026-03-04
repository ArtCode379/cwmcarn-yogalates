package com.cwmcarnyogalates.app.data.repository

import com.cwmcarnyogalates.app.data.datastore.UserManager
import com.cwmcarnyogalates.app.data.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userManager: UserManager
) : UserRepository {
    override fun observeUser(): Flow<UserModel?> = userManager.observeUser()
    override fun observeUserName(): Flow<String?> = userManager.observeUserName()
    override suspend fun saveUser(user: UserModel) = userManager.storeUser(user)
}
