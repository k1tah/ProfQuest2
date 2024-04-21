package com.example.profquest2.ui.composables.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.domain.model.File
import com.example.profquest2.ui.composables.images.RemoteImage
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun PostImage(fileId: String, modifier: Modifier = Modifier) =
    RemoteImage(
        fileId = fileId,
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        loadingDialogSize = 32.dp
    )

@Composable
fun PostIcon(fileId: String, modifier: Modifier = Modifier) =
    RemoteImage(
        fileId = fileId,
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(100)),
        onError = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = ProfQuest2Theme.colors.tertiary,
                modifier = Modifier.size(48.dp)
            )
        }
    )


@Composable
fun PostImages(images: List<File>, onClick: () -> Unit) {
    Box(modifier = Modifier.clickable { onClick() }, contentAlignment = Alignment.Center) {
        when (images.size) {
            1 -> PostImage(fileId = images.first().id.toString())


            2 -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PostImage(
                        fileId = images.first().id.toString(),
                        modifier = Modifier.weight(0.5f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    PostImage(
                        fileId = images[1].id.toString(),
                        modifier = Modifier.weight(0.5f)
                    )
                }
            }

            3 -> {
                Column {
                    PostImage(fileId = images[0].id.toString(), modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PostImage(
                            fileId = images[1].id.toString(),
                            modifier = Modifier.weight(0.5f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        PostImage(
                            fileId = images[2].id.toString(),
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
            }

            else -> {
                Column {
                    PostImage(fileId = images[0].id.toString(), modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PostImage(
                            fileId = images[1].id.toString(),
                            modifier = Modifier.weight(0.5f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        PostImage(
                            fileId = images[2].id.toString(),
                            modifier = Modifier.weight(0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "+${images.size - 3}")
                }
            }
        }
    }
}