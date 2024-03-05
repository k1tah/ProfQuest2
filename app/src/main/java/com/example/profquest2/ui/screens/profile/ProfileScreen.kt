package com.example.profquest2.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.view.button.PrimaryButton
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.SubtitleText
import com.example.profquest2.ui.view.text.TitleText

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.weight(1f))
            TitleText(text = stringResource(id = R.string.profile))
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                icon = R.drawable.ic_settings,
                modifier = Modifier.clickable { navController.navigate(Destination.Settings.route) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.size(128.dp)) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .align(Alignment.Center)
                    .size(128.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SubtitleText(text = stringResource(id = R.string.fullname))
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(text = "Владимир Владимирович Путин")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SubtitleText(text = stringResource(id = R.string.education))
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(text = "ГАПОУ ПО ПКИПТ (ит-колледж)")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SubtitleText(text = stringResource(id = R.string.group))
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(text = "22ит17")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SubtitleText(text = stringResource(id = R.string.phone))
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(text = "+7 (964) 870-08-95")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            SubtitleText(text = stringResource(id = R.string.document))
            Spacer(modifier = Modifier.width(16.dp))
            BodyText(text = "name.docx")
        }
        Spacer(modifier = Modifier.height(16.dp))

        SubtitleText(text = stringResource(id = R.string.test_results))
        Spacer(modifier = Modifier.height(16.dp))

        BodyText(text = "Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст")

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            onClick = {
                navController.navigate(Destination.EditProfile.route)
            },
            text = stringResource(id = R.string.edit_profile),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}