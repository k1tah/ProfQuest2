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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.button.PrimaryButton

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
            Text(
                text = stringResource(id = R.string.profile),
                style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = null,
                modifier = Modifier.clickable { navController.navigate(Destination.Settings.route) },
                tint = ProfQuest2Theme.colors.onSurface
            )
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
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(text = "ФИО", style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Владимир Владимирович Путин",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Место учебы",
                style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "ГАПОУ ПО ПКИПТ (ит-колледж)",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Группа", style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "22ит17",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Номер телефона",
                style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "+7 (964) 870-08-95",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Документ", style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "name.docx",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Профессиональная склонность по итогам теста:",
            style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст",
            style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
        )
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