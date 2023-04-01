package com.dongeul.yogi.github_users.domain.use_case.get_users

import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.model.toUser
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import com.dongeul.yogi.github_users.domain.util.UserOrder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userOrder: UserOrder = UserOrder.OrderAll): Flow<Resource<List<User>>> =
        callbackFlow {
            try {
                trySend(Resource.Loading())
                val users = repository.getItems().items
                repository.insertOrUpdateUsers(users.map { it.toUser() })
                repository.getUsers().collectLatest {
                    when (userOrder) {
                        is UserOrder.OrderAll -> {
                            trySend(Resource.Success(it))
                        }
                        is UserOrder.OrderLike -> {
                            trySend(Resource.Success(it.filter { it.isLike }))
                        }
                        is UserOrder.OrderNormal -> {
                            trySend(Resource.Success(it.filter { !it.isLike }))
                        }
                    }
                }
            } catch (e: HttpException) {
                trySend(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch (e: IOException) {
                trySend(Resource.Error("Check your internet connection."))
            } catch (e: Exception) {
                trySend(Resource.Error(e.message ?: e.localizedMessage))
            }
            awaitClose()
        }
}