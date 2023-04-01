package com.dongeul.yogi.github_users.domain.use_case.get_user.data.repository

import com.dongeul.yogi.github_users.data.data_source.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.data.data_source.remote.dto.UserDto
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private val users = mutableListOf<User>()
    private var itemDto: ItemsDto

    init {
        val userDto = mutableListOf<UserDto>()
        ('a'..'z').forEachIndexed { index, c ->
            userDto.add(
                UserDto(
                    id = index,
                    avatar_url = c.toString(),
                    html_url = c.toString(),
                    login = c.toString()
                )
            )
        }
        itemDto = ItemsDto(incompleteResults = false, items = userDto, totalCount = 0)
    }

    override suspend fun getItems(): ItemsDto {
        return itemDto
    }

    override suspend fun insertOrUpdateUsers(users: List<User>) {

        for (user in users) {
            if (this.users.contains(user)) {
                updateUser(user)
            } else {
                this.users.add(user)
            }
        }

    }

    override suspend fun updateUser(user: User) {
        users.find { it.id == user.id }.let {
            it?.copy(
                avatarUrl = user.avatarUrl,
                htmlUrl = user.htmlUrl,
                login = user.login,
                isLike = user.isLike
            )
        }
    }

    override fun getUsers(): Flow<List<User>> {
        return flow { emit(users) }
    }

    override fun getUserById(id: Int): Flow<User?> {
        return flow { emit(users.find { it.id == id }) }
    }
}