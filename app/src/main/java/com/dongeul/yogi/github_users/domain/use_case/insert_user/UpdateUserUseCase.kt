package com.dongeul.yogi.github_users.domain.use_case.insert_user

import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.updateUser(user)
    }
}