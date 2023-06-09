package com.dongeul.yogi.github_users.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.dongeul.yogi.R
import com.dongeul.yogi.github_users.common.TestTags
import com.dongeul.yogi.github_users.domain.model.User

@Composable
fun HeartIcon(modifier: Modifier = Modifier, user: User?, onItemClick: (User?) -> Unit) {
    IconButton(modifier = modifier.testTag(TestTags.HEART_BUTTON+user?.id), onClick = {
        onItemClick(user)
    }) {
        Icon(
            painter = painterResource(
                id = if (user?.isLike == true) R.drawable.icon_heartfilled
                else R.drawable.icon_heart
            ),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp),
            tint = if (user?.isLike == true) Color.Red else Color.Gray
        )
    }
}