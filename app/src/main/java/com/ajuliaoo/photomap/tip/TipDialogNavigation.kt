package com.ajuliaoo.photomap.tip

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.ajuliaoo.photomap.Tip

fun NavGraphBuilder.addTipDialogRoute(onDismissRequest: () -> Unit) {
    dialog<Tip> {
        TipDialog(modifier = Modifier.fillMaxWidth(), onDismissRequest = onDismissRequest)
    }
}