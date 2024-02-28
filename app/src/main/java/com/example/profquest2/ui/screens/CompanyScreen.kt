package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun CompanyScreen(navController: NavController) {
    var infoExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            modifier = Modifier.clickable {
                navController.popBackStack()
            },
            tint = ProfQuest2Theme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(100)
                    )
                    .size(108.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "АО «НИИЭМП»",
                    style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "@niiemp",
                    style = ProfQuest2Theme.typography.label.copy(fontSize = 14.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Научно-исследовательский институт электронно-механических приборов",
            style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                tint = ProfQuest2Theme.colors.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Присоединились в Январе 2024",
                style = ProfQuest2Theme.typography.label.copy(fontSize = 14.sp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                tint = ProfQuest2Theme.colors.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "г Пенза, ул Каракозова, стр. 44",
                style = ProfQuest2Theme.typography.label.copy(
                    fontSize = 14.sp,
                    color = ProfQuest2Theme.colors.onSurface
                ),
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_link),
                        contentDescription = null,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_link),
                        contentDescription = null,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_link),
                        contentDescription = null,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "+79374212573",
                        style = ProfQuest2Theme.typography.body.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.onSurface
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Пн-пт: 08:00–17:00 Перерыв: 12:00–13:00",
                        style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Электронная почта:",
                style = ProfQuest2Theme.typography.title.copy(
                    fontSize = 16.sp,
                    color = ProfQuest2Theme.colors.onSurface
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "aoNimp@bk.ru",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
        }
        AnimatedVisibility(visible = infoExpanded) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Краткая информация:",
                    style = ProfQuest2Theme.typography.title.copy(fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "НИИЭМП. АО Пензенский научно-исследовательский институт электронно-механических приборов Основание 1959 Основатели СССР. Расположение, Пенза, ул. Каракозова, 44 Отрасль электроника",
                    style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Основной вид деятельности:",
                    style = ProfQuest2Theme.typography.title.copy(
                        fontSize = 16.sp,
                        color = ProfQuest2Theme.colors.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Научные исследования и разработки в области естественных и технических наук прочие",
                    style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Text(
            text = if (infoExpanded) "Свернуть" else "Показать больше",
            style = ProfQuest2Theme.typography.label.copy(fontSize = 16.sp),
            modifier = Modifier.clickable { infoExpanded = !infoExpanded }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Посты",
            style = ProfQuest2Theme.typography.title.copy(
                fontSize = 14.sp,
                color = ProfQuest2Theme.colors.onSurface
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ProfQuest2Theme.colors.tertiary)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                CompanyNewsItem()
            }
        }
    }
}

@Composable
fun CompanyNewsItem() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.niiemp),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "НИИЭМП",
                    style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "12.02.2024 09:00", style = ProfQuest2Theme.typography.label)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "О да! Для вас мы открылись!",
            style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}