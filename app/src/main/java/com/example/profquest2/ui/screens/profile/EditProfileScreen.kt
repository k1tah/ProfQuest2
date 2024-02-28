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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.views.buttons.PrimaryButton
import com.example.profquest2.ui.views.textFields.ProfileInfoField

@Composable
fun EditProfileScreen(navController: NavController) {
    var fullName by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.profile),
                style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.size(128.dp)) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(100)
                    )
                    .align(Alignment.Center)
                    .size(128.dp),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.BottomEnd
                ), tint = ProfQuest2Theme.colors.primary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.fullname),
            value = fullName,
            onValueChange = {
                fullName = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.education),
            value = fullName,
            onValueChange = {
                fullName = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            ProfileInfoField(
                label = stringResource(id = R.string.phone),
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            ProfileInfoField(
                label = stringResource(id = R.string.group),
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                modifier = Modifier.fillMaxWidth(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.select_document),
            value = fullName,
            onValueChange = {
                fullName = it
            },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_doc),
                    contentDescription = null,
                    tint = ProfQuest2Theme.colors.primary
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            onClick = { navController.popBackStack() },
            text = stringResource(id = R.string.done),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}