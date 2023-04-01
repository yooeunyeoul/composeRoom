package com.dongeul.yogi.github_users.domain.use_case.get_user

import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<User>> = callbackFlow {
        try {
            trySend(Resource.Loading())
            val result = repository.getUserById(userId)
            result.collectLatest {
                trySend(Resource.Success(it?:User()))
            }
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: e.localizedMessage))
        }
        awaitClose()
    }
}