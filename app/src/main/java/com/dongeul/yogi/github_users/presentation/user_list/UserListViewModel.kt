package com.dongeul.yogi.github_users.presentation.user_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.use_case.get_users.GetUsersUseCase
import com.dongeul.yogi.github_users.domain.use_case.insert_user.UpdateUserUseCase
import com.dongeul.yogi.github_users.domain.util.UserOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val _state = mutableStateOf(UsersListState())
    val state: State<UsersListState> = _state

    private var getUsersJob: Job? = null

    init {
        getUsers(UserOrder.OrderAll)
    }

    private fun getUsers(order: UserOrder) {
        getUsersJob?.cancel()
        getUsersJob=getUserUseCase(order).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(users = result.data ?: emptyList(), isLoading = false, userOrder = order)
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(error = result.message ?: "error occur", isLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(user: User?) {
        viewModelScope.launch {
            user?.let { user ->
                updateUserUseCase(user.copy(isLike = !user.isLike))

            }
        }
    }

    fun orderEvent(order: UserOrder) {
        if (state.value.userOrder == order) {
            return
        }
        getUsers(order)
    }

    fun toggleOrderSection() {
        _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
    }
}