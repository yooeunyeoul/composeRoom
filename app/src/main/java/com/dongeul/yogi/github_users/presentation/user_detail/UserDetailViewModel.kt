package com.dongeul.yogi.github_users.presentation.user_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongeul.yogi.github_users.common.Constants
import com.dongeul.yogi.github_users.common.Resource
import com.dongeul.yogi.github_users.domain.model.User
import com.dongeul.yogi.github_users.domain.use_case.get_user.GetUserUseCase
import com.dongeul.yogi.github_users.domain.use_case.insert_user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(UserDetailState())
    val state: State<UserDetailState> = _state

    init {
        savedStateHandle.get<Int>(Constants.PARAM_USER_ID)?.let { userId ->
            getUser(userId)
        }
    }

    private fun getUser(userId: Int) {
        getUserUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(user = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(error = result.message ?: "Something wrong", isLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
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

//fun ViewModel.updateUser(user:User?, func:(User)->Unit) {
//    viewModelScope.launch {
//        user?.let {user->
//            func(user)
////            updateUserUseCase(user.copy(isLike = !user.isLike))
//        }
//    }
//
//}
