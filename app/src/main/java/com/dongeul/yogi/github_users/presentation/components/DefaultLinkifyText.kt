package com.dongeul.yogi.github_users.presentation.components

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Patterns
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat

@Composable
fun DefaultLinkifyText(modifier: Modifier = Modifier, text: String?) {
    val context = LocalContext.current
    val customLinkifyTextView = remember {
        TextView(context)
    }
    AndroidView(modifier = modifier, factory = { customLinkifyTextView }) { textView ->
        textView.text = text ?: ""
        textView.textSize= 22f
        LinkifyCompat.addLinks(textView, Linkify.ALL)
        Linkify.addLinks(textView, Patterns.PHONE,"tel:",
            Linkify.sPhoneNumberMatchFilter, Linkify.sPhoneNumberTransformFilter)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}