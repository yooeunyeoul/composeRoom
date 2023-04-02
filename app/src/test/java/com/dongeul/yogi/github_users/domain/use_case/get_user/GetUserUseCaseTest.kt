package com.dongeul.yogi.github_users.domain.use_case.get_user

import com.dongeul.yogi.github_users.data.data_source.remote.dto.UserDto
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.model.toUser
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import com.dongeul.yogi.github_users.domain.use_case.get_user.data.repository.FakeUserRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserUseCaseTest {
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var fakeRepository: UserRepository

    @Before
    fun setUp() {
        fakeRepository = FakeUserRepository()
        getUserUseCase = GetUserUseCase(fakeRepository)

        val userDto = mutableListOf<UserDto>()
        ('a'..'z').forEachIndexed { index, c ->
            userDto.add(
                UserDto(
                    id = index,
                    avatar_url = c.toString(),
                    html_url = c.toString(),
                    login = c.toString(),
                )
            )
        }
        runTest {
            fakeRepository.insertOrUpdateUsers(userDto.map { it.toUser() })
        }

    }

    @Test
    fun `유저아이디 1번을 호출 했을 때 해당 유저가 출력이 되는가`() {
        runTest {
            var lastItem: User? = null
            val user = getUserUseCase(userId = 1).onEach {
                lastItem = it.data
            }
            val collectJob = launch { user.collect() }
            advanceUntilIdle()
            collectJob.cancel()
            assertThat(lastItem?.id).isEqualTo(1)

        }
    }
}