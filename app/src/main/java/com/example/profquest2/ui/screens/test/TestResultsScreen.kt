package com.example.profquest2.ui.screens.test

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profquest2.R
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.utils.Results
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestResultsScreen(results: Results, testsViewModel: TestsViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0, pageCount = { 5 })
    fun moveToPage(page: Int) = scope.launch {
        pagerState.animateScrollToPage(page)
    }

    LaunchedEffect(true) {
        testsViewModel.setGollandTestResult(results.type + results.specs + results.orientation + results.professionalEnvironment)
    }

    val pages =
        listOf<@Composable () -> Unit>(
            {
                SpecsPage(results.type, results.description) { moveToPage(1) }
            },
            {
                SpecsPage(stringResource(R.string.specs), results.specs) { moveToPage(2) }
            },
            {
                SpecsPage(
                    stringResource(R.string.orientation),
                    results.orientation
                ) { moveToPage(3) }
            },
            {
                SpecsPage(
                    stringResource(R.string.enviroment),
                    results.professionalEnvironment
                ) { moveToPage(4) }
            },
            {
                ProfessionsPage(results)
            }
        )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                if (it.x > size.width / 2) {
                    moveToPage(pagerState.currentPage + 1)
                } else {
                    moveToPage(pagerState.currentPage - 1)
                }
            })
        }) {
        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(state = pagerState) {
            pages[it].invoke()
        }

        Spacer(modifier = Modifier.weight(1f))

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(4) {
                Icon(
                    icon = R.drawable.ic_circle,
                    modifier = Modifier.size(if (it == pagerState.currentPage) 12.dp else 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SpecsPage(title: String, specs: String, onNext: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TitleText(text = title)
        Spacer(modifier = Modifier.height(16.dp))

        BodyText(text = specs, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))

        androidx.compose.material3.Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.clickable { onNext() }
        )
    }
}

@Composable
fun ProfessionsPage(results: Results) {
    var image1Visible by remember {
        mutableStateOf(false)
    }
    var image2Visible by remember {
        mutableStateOf(false)
    }
    var image3Visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        image1Visible = true
        delay(1000)
        image2Visible = true
        delay(1000)
        image3Visible = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedImage(visible = image1Visible, image = results.images[0], Alignment.BottomStart)
        AnimatedImage(visible = image3Visible, image = results.images[1], Alignment.CenterEnd)
        AnimatedImage(visible = image2Visible, image = results.images[2], Alignment.TopStart)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            TitleText(text = stringResource(R.string.professions))
            Spacer(modifier = Modifier.height(16.dp))

            BodyText(
                text = results.professions,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun BoxScope.AnimatedImage(visible: Boolean, @DrawableRes image: Int, align: Alignment) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        ) + scaleIn(),
        modifier = Modifier
            .align(align)
            .padding(start = 48.dp, bottom = 128.dp)
    ) {
        RotatedImage(
            res = image,
            rotation = 30f
        )
    }
}

@Composable
fun RotatedImage(res: Int, rotation: Float, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = res),
        contentDescription = null,
        modifier = modifier
            .graphicsLayer {
                rotationZ = rotation
            }
            .size(height = 150.dp, width = 110.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        alpha = 0.3f
    )
}