package com.dongeul.yogi.github_users.presentation.user_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dongeul.yogi.github_users.common.Constants
import com.dongeul.yogi.github_users.domain.use_case.get_user.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase,
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
//        getUserUseCase().onEach { result ->
//            when (result) {
//                is Resource.Success -> {
//                    _state.value = UserDetailState(user = )
//                }
//                is Resource.Error -> {
//                    _state.value = UserDetailState(error = result.message ?: "error occur")
//                }
//                is Resource.Loading -> {
//                    _state.value = UserDetailState(isLoading = true)
//                }
//            }
//        }.launchIn(viewModelScope)
    }
}