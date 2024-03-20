package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.extensions.toPx
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.LabelText
import com.example.profquest2.ui.view.text.SubtitleText
import com.example.profquest2.ui.view.text.TitleText
import com.example.profquest2.ui.view.textField.SearchField
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
                        modifier = Modifier.size(160.dp, 64.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        icon = R.drawable.ic_search,
                        modifier = Modifier.clickable {
                            isSearchVisible = true
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(icon = R.drawable.ic_notification)
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
                        navController.navigate(Destination.Company.route)
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
                                GoodNewsCard()
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
        androidx.compose.material3.Icon(
            painter = painterResource(id = R.drawable.niiemp),
            contentDescription = null,
            tint = ProfQuest2Theme.colors.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            SubtitleText(text = "НИИЭМП")
            Spacer(modifier = Modifier.height(2.dp))
            LabelText(text = "Пенза, ул. Каракозова 88")
        }
    }
}

@Composable
fun GoodNewsCard() {
    var isLiked by rememberSaveable {
        mutableStateOf(false)
    }
    var showPopup by rememberSaveable {
        mutableStateOf(false)
    }
    var isSelected by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box {
            if (isSelected) {
                Icon(
                    icon = R.drawable.ic_three_dots_horiz,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable { showPopup = true }
                )
            }
            if (showPopup) {
                Popup(
                    alignment = Alignment.TopEnd,
                    offset = IntOffset(x = (-4).toPx(), y = 32.toPx()),
                    onDismissRequest = { showPopup = false }
                ) {
                    OutlinedButton(
                        onClick = {
                            isSelected = false
                            showPopup = false
                        },
                        border = BorderStroke(1.dp, ProfQuest2Theme.colors.secondary)
                    ) {
                        Icon(icon = R.drawable.ic_close)
                        Spacer(modifier = Modifier.width(4.dp))
                        BodyText(text = stringResource(R.string.cancel_vote))
                    }
                }
            }
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
                BodyText(text = "О нет! Мы закрылись!")
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.kotik),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Survey("Опросный опрос", isSelected) {
                    isSelected = true
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        icon = if (isLiked) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                        modifier = Modifier.clickable { isLiked = !isLiked }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    BodyText(text = "1023")
                }
            }
        }
    }
}

data class Question(
    val text: String,
    val count: Int
)

@Composable
fun Survey(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    val questions = listOf(
        Question("var 1", 10),
        Question("var 2", 10),
        Question("var 3", 10)
    )
    Column {
        Icon(icon = R.drawable.ic_stats)
        Spacer(modifier = Modifier.height(8.dp))
        BodyText(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        questions.forEach {
            SurveyItem(text = it.text, votesCount = it.count, isSelected) {
                onSelect()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = ProfQuest2Theme.colors.secondary)
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelText(text = "77 голосов")
            Icon(icon = R.drawable.ic_circle)
            LabelText(text = "7 дней до окончания")
        }
    }
}

@Composable
fun SurveyItem(text: String, votesCount: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp)
            .height(48.dp)
            .border(1.dp, ProfQuest2Theme.colors.secondary, shape = RoundedCornerShape(4.dp))
    ) {
        AnimatedVisibility(
            visible = isSelected,
            modifier = Modifier.align(Alignment.CenterStart),
            enter = expandHorizontally()
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                ProfQuest2Theme.colors.tertiary,
                                ProfQuest2Theme.colors.surface
                            )
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxWidth(0.5f)
                    .height(48.dp),
                content = {},
                color = Color.Transparent
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            BodyText(text = text)
            Spacer(modifier = Modifier.weight(1f))
            BodyText(text = votesCount.toString())
        }
    }
}
