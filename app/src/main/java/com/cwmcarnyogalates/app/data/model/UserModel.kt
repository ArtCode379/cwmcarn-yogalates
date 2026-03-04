package com.cwmcarnyogalates.app.data.model

data class UserModel(
    val name: String,
    val email: String = "",
    val height: Int = 0,
    val weight: Float = 0f,
    val age: Int = 0,
    val goalWorkouts: Int = 0,
)
