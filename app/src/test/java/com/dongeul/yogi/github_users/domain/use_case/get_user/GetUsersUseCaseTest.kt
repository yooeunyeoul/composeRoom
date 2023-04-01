package com.dongeul.yogi.github_users.domain.use_case.get_user

import com.dongeul.yogi.github_users.data.data_source.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.data.data_source.remote.dto.UserDto
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import com.dongeul.yogi.github_users.domain.use_case.get_user.data.repository.FakeUserRepository
import com.dongeul.yogi.github_users.domain.use_case.get_users.GetUsersUseCase
import com.dongeul.yogi.github_users.domain.util.UserOrder
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
class GetUsersUseCaseTest {
    private lateinit var getUserUseCase: GetUsersUseCase
    private lateinit var fakeRepository: UserRepository
    private lateinit var itemDto: ItemsDto

    @Before
    fun setUp() {
        fakeRepository = FakeUserRepository()
        getUserUseCase = GetUsersUseCase(fakeRepository)

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
        itemDto = ItemsDto(incompleteResults = false, items = userDto, totalCount = 0)

    }

    @Test
    fun `Sort All 로 했을 때 모든 유저딀이 모든 데이터가 호출 되는가`() {
        runTest {
            var lastItem: List<User>? = null
            val users = getUserUseCase(UserOrder.OrderAll).onEach {
                lastItem = it.data
            }
            val collectJob = launch { users.collect() }
            advanceUntilIdle()
            collectJob.cancel()
            assertThat(lastItem?.size).isEqualTo(itemDto.items.size)

        }

    }

    @Test
    fun `DB에 좋아요 목록이 없을 때 Sort Like 로 호출시 빈목록이 호출 되는가  `() {
        runTest {
            var lastItem: List<User>? = null
            val users = getUserUseCase(UserOrder.OrderLike).onEach {
                lastItem = it.data
            }
            val collectJob = launch { users.collect() }
            advanceUntilIdle()
            collectJob.cancel()
            assertThat(lastItem).isEmpty()
        }
    }

    @Test
    fun `Sort Normal 로 호출 시 일반 게시물만 출력되는가`() {
        runTest {
            var lastItem: List<User>? = null
            val users = getUserUseCase(UserOrder.OrderNormal).onEach {
                lastItem = it.data
            }
            val collectJob = launch { users.collect() }
            advanceUntilIdle()
            collectJob.cancel()
            val likeUser = lastItem?.find { it.isLike }
            assertThat(likeUser).isNull()

        }
    }
}