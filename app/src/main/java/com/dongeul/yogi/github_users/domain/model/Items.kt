package com.dongeul.yogi.github_users.domain.model

import com.dongeul.yogi.github_users.data.remote.dto.ItemsDto
import com.dongeul.yogi.github_users.data.remote.dto.UserDto

class Items(
    val items: List<UserDto>
) {
}

fun ItemsDto.toItems():Items {
    return Items(items = items)
}