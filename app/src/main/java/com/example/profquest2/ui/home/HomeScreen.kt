package com.example.profquest2.ui.home

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.data.api.BASE_URL
import com.example.domain.model.Post
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
import com.example.profquest2.utils.OnBottomReached
import com.example.profquest2.utils.showShortToast
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val tabs = listOf("Все новости", "Для вас")
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val scope = rememberCoroutineScope()

    val state = viewModel.collectAsState().value
    val scrollState = rememberLazyListState()
    var currentPage by rememberSaveable {
        mutableIntStateOf(0)
    }
    scrollState.OnBottomReached { viewModel.getPosts(page = ++currentPage) }

    val context = LocalContext.current

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    viewModel.collectSideEffect {
        isLoading = when (it) {
            is HomeSideEffect.Done -> false


            is HomeSideEffect.Error -> {
                context.showShortToast(it.message)
                false
            }

            is HomeSideEffect.Loading -> true
        }
    }

    if (isLoading) {
        BasicAlertDialog(onDismissRequest = { }, modifier = Modifier.size(64.dp)) {
            CircularProgressIndicator(color = ProfQuest2Theme.colors.primary)
        }
    }
    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(state = refreshState, onRefresh = { viewModel.getPosts() }) {
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

                        }

                        0 -> {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                state = scrollState
                            ) {
                                items(state.posts) {
                                    Post(
                                        post = it,
                                        onLike = { postId ->
                                            viewModel.like(postId)
                                        },
                                        onVote = { postId, variant ->
                                            viewModel.vote(postId, variant)
                                        },
                                        onUndoVote = { postId ->
                                            viewModel.undoVote(postId)
                                        }
                                    )
                                }
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
fun Post(
    post: Post,
    onLike: (Long) -> Unit,
    onVote: (Long, Int) -> Unit,
    onUndoVote: (Long) -> Unit
) {
    var showPopup by rememberSaveable {
        mutableStateOf(false)
    }

    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale("ru")).parse(post.date)
    val formattedDate = date?.let { SimpleDateFormat("yyyy.MM.dd HH:mm", Locale("ru")).format(it) }

    var expirationDate: Date?
    var daysLeft: Int? = null
    var isExpired: Boolean? = null

    post.expirationDate?.let {
        expirationDate = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            Locale("ru")
        ).parse(it)
        daysLeft = (Date(expirationDate!!.time - Date().time).time / 86400000).toInt() - 1
        isExpired = daysLeft!! < 0
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box {
            if (post.isVoted == true && isExpired == false) {
                Icon(
                    icon = R.drawable.ic_three_dots_horiz,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clickable { showPopup = !showPopup }
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
                            onUndoVote(post.id)
                            showPopup = false
                        },
                        border = BorderStroke(1.dp, ProfQuest2Theme.colors.secondary),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(icon = R.drawable.ic_close)
                        Spacer(modifier = Modifier.width(4.dp))
                        BodyText(text = stringResource(R.string.cancel_vote))
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SubcomposeAsyncImage(
                        model = BASE_URL + "file/${post.icon.id}",
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(100))
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        TitleText(text = post.name)
                        Spacer(modifier = Modifier.height(2.dp))
                        LabelText(text = formattedDate ?: "")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                BodyText(text = post.text)
                Spacer(modifier = Modifier.height(24.dp))

                if (post.files?.isNotEmpty() == true) {
                    SubcomposeAsyncImage(
                        model = BASE_URL + "file/${post.files?.first()?.id}",
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (post.votes != null && post.variants != null && post.isVoted != null && post.expirationDate != null) {
                    Survey(
                        post.variants!!,
                        post.votes!!,
                        post.isVoted!!,
                        { onVote(post.id, it) },
                        isExpired!!,
                        daysLeft!!
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Icon(
                        icon = if (post.isLiked) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                        modifier = Modifier.clickable { onLike(post.id) }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    BodyText(text = post.likes.toString())
                }
            }
        }
    }
}

@Composable
fun Survey(
    questions: List<String>,
    votes: List<Int>,
    isSelected: Boolean,
    onSelect: (Int) -> Unit,
    isExpired: Boolean,
    daysLeft: Int
) {
    Column {
        Icon(icon = R.drawable.ic_stats)
        Spacer(modifier = Modifier.height(16.dp))

        questions.forEachIndexed { index, item ->
            SurveyItem(
                text = item,
                votesCount = votes[index],
                isSelected = isExpired || isSelected,
                percent = if (votes.sum() != 0) (votes[index].toFloat() / votes.sum()
                    .toFloat()) else 0f,
                onSelect = { if (!isExpired) onSelect(index) }
            )
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
            LabelText(text = "${votes.sum()} голосов")
            Icon(icon = R.drawable.ic_circle)
            LabelText(
                text = when (daysLeft) {
                    in 1..Int.MAX_VALUE -> "$daysLeft дней до окончания"

                    0 -> "Сегодня последний день голосования"

                    else -> "Голосование завершено"
                }
            )
        }
    }
}

@Composable
fun SurveyItem(
    text: String,
    votesCount: Int,
    isSelected: Boolean,
    onSelect: () -> Unit,
    percent: Float
) {
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
                    .fillMaxWidth(percent)
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
            if (isSelected) BodyText(text = votesCount.toString())
        }
    }
}
