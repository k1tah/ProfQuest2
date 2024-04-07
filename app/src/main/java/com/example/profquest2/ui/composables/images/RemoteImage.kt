package com.example.profquest2.ui.composables.images

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.data.api.BASE_URL
import com.example.profquest2.ui.composables.dialog.LoadingDialog

@Composable
fun RemoteImage(
    fileId: String,
    modifier: Modifier = Modifier,
    loadingDialogSize: Dp = 64.dp,
    onError: @Composable () -> Unit = {}
) {
    SubcomposeAsyncImage(
        model = BASE_URL + "file/$fileId",
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loading = { LoadingDialog(modifier = modifier.size(loadingDialogSize)) },
        error = { onError() }
    )
}