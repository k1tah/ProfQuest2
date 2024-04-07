package com.example.profquest2.ui.screens.test

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.utils.getSecondTestResult
import com.example.profquest2.utils.questions
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun SecondTestScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val progressBarScrollState = rememberLazyListState()
    var currentQuestion by rememberSaveable {
        mutableIntStateOf(0)
    }
    val listAnswers = rememberSaveable { mutableListOf<Int>() }

    fun addAnswerToList(answer: Int) {
        if (currentQuestion != 23) {
            listAnswers.add(answer)
            currentQuestion++
            scope.launch {
                progressBarScrollState.scrollToItem(currentQuestion, scrollOffset = -128)
            }
        } else {
            navController.navigate(
                "secondTestResults/${Uri.encode(Gson().toJson(getSecondTestResult(listAnswers)))}"
            ) {
                popUpTo(Destination.SelectTest.route)
            }
        }
    }

    Column(
        Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(256.dp),
                state = progressBarScrollState
            ) {
                items(24) {
                    ProgressIndicatorItem(
                        number = it + 1,
                        isCompleted = it + 1 in 1..listAnswers.size
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.clickable { navController.popBackStack() },
                tint = ProfQuest2Theme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(questions[currentQuestion].image),
                contentDescription = null,
                modifier = Modifier.size(height = 305.dp, width = 235.dp),
                contentScale = ContentScale.Crop
            )

            TitleText(
                text = "${currentQuestion + 1}. ${questions[currentQuestion].title}"
            )
            Spacer(modifier = Modifier.height(8.dp))

            AnswerPickerItem(
                text = questions[currentQuestion].variants[0]
            ) {
                addAnswerToList(1)
            }
            Spacer(modifier = Modifier.height(16.dp))

            AnswerPickerItem(
                text = questions[currentQuestion].variants[1]
            ) {
                addAnswerToList(2)
            }
            Spacer(modifier = Modifier.height(16.dp))

            AnswerPickerItem(
                text = questions[currentQuestion].variants[2]
            ) {
                addAnswerToList(3)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ProgressIndicatorItem(
    number: Int,
    isCompleted: Boolean
) {
    Box(
        Modifier
            .size(24.dp)
            .background(
                if (isCompleted) ProfQuest2Theme.colors.primary
                else ProfQuest2Theme.colors.tertiary
            )
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AnswerPickerItem(text: String, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onSelect() }
    ) {
        Icon(
            icon = R.drawable.ic_circle,
            modifier = Modifier.size(8.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))

        BodyText(text = text)
    }
}