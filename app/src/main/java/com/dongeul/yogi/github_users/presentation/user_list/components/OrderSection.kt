package com.dongeul.yogi.github_users.presentation.user_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dongeul.yogi.github_users.common.TestTags
import com.dongeul.yogi.github_users.domain.util.UserOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    userOrder: UserOrder = UserOrder.OrderAll,
    onOrderChange: (UserOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "ALL",
                selected = userOrder is UserOrder.OrderAll,
                onSelect = { onOrderChange(UserOrder.OrderAll) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "LIKE",
                selected = userOrder is UserOrder.OrderLike,
                onSelect = { onOrderChange(UserOrder.OrderLike)},
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "NORMAL",
                selected = userOrder is UserOrder.OrderNormal,
                onSelect = { onOrderChange(UserOrder.OrderNormal) }
            )
        }
    }
}