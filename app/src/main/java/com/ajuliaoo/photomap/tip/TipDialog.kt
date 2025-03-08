package com.ajuliaoo.photomap.tip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ajuliaoo.photomap.R
import com.ajuliaoo.photomap.ui.theme.PhotoMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            ),
        onDismissRequest = onDismissRequest
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_photos),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.tip_text),
                )
                OutlinedButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onDismissRequest,
                    border = null
                ) {
                    Text(stringResource(R.string.ok))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TipDialogPreview() {
    PhotoMapTheme {
        TipDialog(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {}
    }
}