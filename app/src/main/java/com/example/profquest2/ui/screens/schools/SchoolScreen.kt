package com.example.profquest2.ui.screens.schools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.Post
import com.example.profquest2.R
import com.example.profquest2.extensions.formatDate
import com.example.profquest2.extensions.toPx
import com.example.profquest2.ui.composables.dialog.LoadingDialog
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.images.RemoteImage
import com.example.profquest2.ui.composables.post.PostImages
import com.example.profquest2.ui.composables.post.Survey
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.utils.OnBottomReached
import com.example.profquest2.utils.showShortToast
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SchoolScreen(
    navController: NavController,
    id: Long,
    viewModel: SchoolsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getSchool(id)
    }

    val state = viewModel.collectAsState().value

    var infoExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    viewModel.collectSideEffect {
        isLoading = when (it) {
            is SchoolsSideEffect.Done -> false


            is SchoolsSideEffect.Error -> {
                context.showShortToast(it.message)
                false
            }

            is SchoolsSideEffect.Loading -> true
        }
    }

    val refreshState = rememberSwipeRefreshState(isRefreshing = isLoading)


    val scrollState = rememberLazyListState()
    var currentPage by rememberSaveable {
        mutableIntStateOf(0)
    }
    scrollState.OnBottomReached { viewModel.getPosts(id, page = ++currentPage) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Icon(
            icon = R.drawable.ic_arrow_left,
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (state.school == null) {
            LoadingDialog(modifier = Modifier.size(64.dp))
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RemoteImage(
                    state.school.image?.id.toString(),
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(100)),
                    onError = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = ProfQuest2Theme.colors.tertiary,
                            modifier = Modifier.size(128.dp)
                        )
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    TitleText(text = state.school.name)
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = "@" + state.school.nickname)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            BodyText(
                text = state.school.shortDesc,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon = R.drawable.ic_calendar,
                    tint = ProfQuest2Theme.colors.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                LabelText(text = state.school.date)
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row {
                state.school.socials.let { socials ->
                    Column { socials.forEach { LinkItem(link = it) } }
                    Spacer(modifier = Modifier.width(32.dp))
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            icon = R.drawable.ic_phone,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = state.school.phone,
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
                            icon = R.drawable.ic_clock,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        BodyText(text = "Время работы: ${state.school.worktime} ")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                SubtitleText(text = "Электронная почта:")
                Spacer(modifier = Modifier.width(4.dp))
                BodyText(text = state.school.email)
            }

            AnimatedVisibility(visible = infoExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    SubtitleText(text = "Краткая информация:")
                    Spacer(modifier = Modifier.height(4.dp))

                    BodyText(
                        text = state.school.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            LabelText(
                text = if (infoExpanded) "Свернуть" else "Показать больше",
                modifier = Modifier.clickable { infoExpanded = !infoExpanded }
            )
            Spacer(modifier = Modifier.height(8.dp))

            SubtitleText(
                text = "Посты",
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ProfQuest2Theme.colors.tertiary)
            )
            SwipeRefresh(
                state = refreshState,
                onRefresh = { viewModel.refreshPosts(id) }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = scrollState
                ) {
                    items(state.posts) { post ->
                        SchoolPostItem(
                            post = post,
                            onLike = { viewModel.like(it) },
                            onUndoVote = { viewModel.undoVote(it) },
                            onVote = { postId, variant -> viewModel.vote(postId, variant) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LinkItem(link: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon = R.drawable.ic_link,
            tint = ProfQuest2Theme.colors.secondary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = link,
            style = ProfQuest2Theme.typography.label.copy(
                fontSize = 14.sp,
                color = ProfQuest2Theme.colors.primary
            ),
            textDecoration = TextDecoration.Underline
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SchoolPostItem(
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
                Icon(
                    painter = painterResource(id = R.drawable.niiemp),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleText(text = post.name)
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = post.date.formatDate() ?: "")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            BodyText(text = post.text)
            Spacer(modifier = Modifier.height(8.dp))

            if (post.files?.isNotEmpty() == true) {
                val images = post.files!!
                if (showImages) {
                    BasicAlertDialog(onDismissRequest = { showImages = false }) {
                        HorizontalPager(
                            state = rememberPagerState(initialPage = 0) { images.size },
                            contentPadding = PaddingValues(horizontal = 16.dp)
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