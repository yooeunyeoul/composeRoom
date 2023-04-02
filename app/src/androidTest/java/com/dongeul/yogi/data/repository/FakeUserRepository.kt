package com.dongeul.yogi.data.repository

import com.dongeul.yogi.github_users.data.data_source.local.UserDao
import com.dongeul.yogi.github_users.data.data_source.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.data.data_source.remote.dto.UserDto
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeUserRepository @Inject constructor(private val dao: UserDao) : UserRepository {

    private var itemDto: ItemsDto
    val imageList = listOf<String>(
        "https://avatars.githubusercontent.com/u/8085?v=4," +
                "https://avatars.githubusercontent.com/u/2850035?v=4",
        "https://avatars.githubusercontent.com/u/12779751?v=4",
        "https://avatars.githubusercontent.com/u/612628?v=4"
    )
    val htmlList = listOf<String>(
        "https://github.com/Shopify",
        "https://github.com/shopglobal",
        "https://github.com/shopware",
        "https://github.com/shopifypartners"
    )

    init {
        val userDto = mutableListOf<UserDto>()
        ('a'..'z').forEachIndexed { index, c ->
            userDto.add(
                UserDto(
                    id = index,
                    avatar_url = imageList[index % 3],
                    html_url = htmlList[index % 3],
                    login = c.toString()
                )
            )
        }
        itemDto = ItemsDto(incompleteResults = false, items = userDto, totalCount = 0)
    }

    override suspend fun getItems(): ItemsDto =
        itemDto

    override fun getUsers(): Flow<List<User>> =
        dao.getUsers()

    override fun getUserById(id: Int): Flow<User?> =
        dao.getUserById(id)


    override suspend fun insertOrUpdateUsers(users: List<User>) {
        dao.insertOrUpdateUsers(users = users)
    }

    override suspend fun updateUser(user: User) {
        dao.userLikeUpdate(id = user.id ?: return, isLike = user.isLike)
    }
}