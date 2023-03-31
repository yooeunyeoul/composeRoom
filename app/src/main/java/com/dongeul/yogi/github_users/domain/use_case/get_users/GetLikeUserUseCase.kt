package com.dongeul.yogi.github_users.domain.use_case.get_users

import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.LikeUser
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.model.toUser
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLikeUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<LikeUser>>> = flow {
        try {
            emit(Resource.Loading())
            repository.getUsers().collectLatest {likeUsers->
//                emit(Resource.Success(likeUsers))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Can't load database"))
        }
    }
}