package com.dongeul.yogi.github_users.domain.repository

import com.dongeul.yogi.github_users.data.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getItems(): ItemsDto

    suspend fun insertOrUpdateUsers(users: List<User>)

    suspend fun updateUser(user:User)

    fun getUsers(): Flow<List<User>>

    fun getUserById(id: Int): Flow<User?>
}