package com.example.profquest2.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.domain.model.Company
import com.example.domain.model.Post
import com.example.profquest2.R
import com.example.profquest2.extensions.formatDate
import com.example.profquest2.extensions.toPx
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.images.RemoteImage
import com.example.profquest2.ui.composables.post.PostIcon
import com.example.profquest2.ui.composables.post.PostImages
import com.example.profquest2.ui.composables.post.Survey
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.SearchField
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val tabs = listOf(stringResource(R.string.all_news), stringResource(R.string.for_you))
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

    var unauthorized by rememberSaveable {
        mutableStateOf(false)
    }

    viewModel.collectSideEffect {
        isLoading = when (it) {
            is HomeSideEffect.Done -> {
                unauthorized = false
                false
            }


            is HomeSideEffect.Error -> {
                context.showShortToast(it.message)
                false
            }

            is HomeSideEffect.Loading -> true

            is HomeSideEffect.Unauthorized -> {
                unauthorized = true
                false
            }
        }
    }

    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    if (unauthorized) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(text = stringResource(id = R.string.unauthorized))

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(
                onClick = {
                    navController.navigate(Destination.Profile.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                text = stringResource(id = R.string.сontinue)
            )
        }
    } else {
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
                LaunchedEffect(Unit) { viewModel.getCompanies() }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.companies.filter {
                        it.name.contains(
                            searchQuery,
                            ignoreCase = true
                        )
                    }) { company ->
                        CompanyItem(
                            company,
                            onCompanyClick = {
                                navController.navigate(Destination.Company.route + "/${company.id}")
                            }
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(32.dp))

                SwipeRefresh(
                    state = refreshState,
                    onRefresh = { viewModel.refreshPosts() }
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        state = scrollState,
                        modifier = Modifier.fillMaxSize()
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

@Composable
fun CompanyItem(company: Company, onCompanyClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCompanyClick() }
    ) {
        PostIcon(fileId = company.image?.id.toString())
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            SubtitleText(text = company.name)
            company.address?.let {
                Spacer(modifier = Modifier.height(2.dp))
                LabelText(text = it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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

    var showImages by rememberSaveable {
        mutableStateOf(false)
    }

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
                        BodyText(text = stringResource(R.string.vote_cancel))
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PostIcon(fileId = post.icon?.id.toString())
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        TitleText(text = post.name)
                        Spacer(modifier = Modifier.height(2.dp))
                        LabelText(text = post.date.formatDate() ?: "")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                BodyText(text = post.text)
                Spacer(modifier = Modifier.height(24.dp))

                if (post.files?.isNotEmpty() == true) {
                    val images = post.files!!
                    if (showImages) {
                        BasicAlertDialog(onDismissRequest = { showImages = false }) {
                            HorizontalPager(
                                state = rememberPagerState(initialPage = 0) { images.size }
                            ) {
                                RemoteImage(
                                    fileId = images[it].id.toString(),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    PostImages(images = images, onClick = { showImages = true })
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





