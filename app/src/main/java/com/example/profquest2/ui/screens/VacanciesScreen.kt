package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.LabelText
import com.example.profquest2.ui.view.text.SubtitleText
import com.example.profquest2.ui.view.text.TitleText
import com.example.profquest2.ui.view.textField.SearchField

@Composable
fun VacanciesScreen() {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(64.dp)
        ) {
            AnimatedVisibility(visible = !isSearchVisible) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon = R.drawable.ic_favorites)
                    Spacer(modifier = Modifier.weight(1f))
                    TitleText(text = stringResource(id = R.string.vacansies))
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        icon = R.drawable.ic_search,
                        modifier = Modifier.clickable {
                            isSearchVisible = true
                        }
                    )
                }
            }
            AnimatedVisibility(visible = isSearchVisible, enter = slideInHorizontally()) {
                SearchField(
                    value = searchQuery,
                    onValueChanged = { searchQuery = it },
                    onClose = { isSearchVisible = false }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                VacancyCard()
            }
        }
    }
}

@Composable
fun VacancyCard() {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    var isFavorite by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.niiemp),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleText(text = "НИИЭМП")
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = "12.02.2024 09:00")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleText(text = "Прораммист-техник")
            Spacer(modifier = Modifier.height(4.dp))
            TitleText(text = "500000 Р")
            Spacer(modifier = Modifier.height(16.dp))
            SubtitleText(text = "Описание")
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Описание Описание Описание Описание Описание Описание",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface),
                maxLines = if (!expanded) 1 else 100
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (!expanded) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LabelText(
                        text = stringResource(R.string.show_more),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        icon = if (isFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                        modifier = Modifier.clickable { isFavorite = !isFavorite }
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    SubtitleText(text = stringResource(R.string.duties))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.requirements))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.working_conditions))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.skills))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.contacts))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.address))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = stringResource(R.string.description) )
                    Spacer(modifier = Modifier.height(8.dp))

                    LabelText(
                        text = stringResource(R.string.hide),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            }
        }
    }
}