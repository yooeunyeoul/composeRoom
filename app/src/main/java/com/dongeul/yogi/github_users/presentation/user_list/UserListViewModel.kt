package com.dongeul.yogi.github_users.presentation.user_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.use_case.get_user.GetUsersUseCase
import com.dongeul.yogi.github_users.domain.use_case.insert_user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _state = mutableStateOf(UsersListState())
    val state: State<UsersListState> = _state

    init {
        getUsers()
    }

    private fun getUsers() {
        getUserUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = UsersListState(users = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = UsersListState(error = result.message ?: "error occur")
                }
                is Resource.Loading -> {
                    _state.value = UsersListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(user: User?) {
        viewModelScope.launch {
            user?.let {user->
                updateUserUseCase(user.copy(isLike = !user.isLike))
            }
        }
    }
}