package com.dongeul.yogi.github_users.domain.use_case.get_user

import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.model.toUser
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = channelFlow {
        try {
            send(Resource.Loading())
            val users = repository.getItems().items
            repository.insertOrUpdateUsers(users.map { it.toUser() })
            repository.getUsers().collectLatest {
                send(Resource.Success(it))
            }
        } catch (e: HttpException) {
            send(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            send(Resource.Error("Check your internet connection."))
        } catch (e: Exception) {
            send(Resource.Error("Something Wrong"))
        }
    }
}