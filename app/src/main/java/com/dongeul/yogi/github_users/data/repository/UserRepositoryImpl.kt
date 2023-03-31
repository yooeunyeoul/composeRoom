package com.dongeul.yogi.github_users.data.repository

import com.dongeul.yogi.github_users.data.data_source.UserDao
import com.dongeul.yogi.github_users.data.remote.GithubApi
import com.dongeul.yogi.github_users.data.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val dao: UserDao
) : UserRepository {
    override suspend fun getItems(): ItemsDto =
        api.getItem()


    override fun getUsers(): Flow<List<User>> =
        dao.getUsers()

    override suspend fun insertUsers(users: List<User>) {
        dao.insertUsers(users = users)
    }

    override suspend fun insertOrUpdateUsers(users: List<User>) {
        dao.insertOrUpdateUsers(users = users)
    }

    override suspend fun updateUser(user: User) {
        dao.userLikeUpdate(id = user.id ?: return, isLike = user.isLike)
    }

}