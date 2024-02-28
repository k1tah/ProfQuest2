package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val tabs = listOf("Все новости", "Для вас")
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val scope = rememberCoroutineScope()
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(64.dp)
        ) {
            AnimatedVisibility(visible = !isSearchVisible) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = ProfQuest2Theme.images.logo),
                        contentDescription = null,
                        Modifier.size(160.dp, 64.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            isSearchVisible = true
                        },
                        tint = ProfQuest2Theme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = null,
                        tint = ProfQuest2Theme.colors.onSurface
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
        if (isSearchVisible) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(10) {
                    CompanyItem {
                        navController.navigate("company")
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.height(32.dp))
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                contentColor = ProfQuest2Theme.colors.onSurface,
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(it[pagerState.currentPage])
                            .height(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(horizontal = 28.dp)
                            .background(color = ProfQuest2Theme.colors.primary)
                    )
                }) {
                tabs.forEachIndexed { index, s ->
                    Tab(
                        selected = (index == pagerState.currentPage),
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        text = { Text(text = s) }
                    )
                }
            }
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    1 -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(1) {
                                NewsCard()
                            }
                        }
                    }

                    0 -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(5) {
                                GoodNewsCard()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompanyItem(onNavigateToCompany: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToCompany() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.niiemp),
            contentDescription = null,
            tint = ProfQuest2Theme.colors.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "НИИЭМП",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Пенза, ул. Каракозова 88", style = ProfQuest2Theme.typography.label)
        }
    }
}

@Composable
fun GoodNewsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "О нет! Мы закрылись!",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.kotik),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun NewsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "О да! Для вас мы открылись!",
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.height(24.dp))
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
}

@Composable
fun SearchField(value: String, onValueChanged: (String) -> Unit, onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            value = value,
            onValueChange = onValueChanged,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onClose()
                    },
                    tint = ProfQuest2Theme.colors.onSurface
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null,
                    tint = ProfQuest2Theme.colors.onSurface
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = ProfQuest2Theme.colors.primary
            ),
            placeholder = {
                Text(text = "Поиск", style = ProfQuest2Theme.typography.label)
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}