package com.example.profquest2.ui.screens.test

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.utils.getTestResult
import com.example.profquest2.utils.listVariants
import com.google.gson.Gson

@Composable
fun TestScreen(navController: NavController) {
    var currentQuestion by rememberSaveable {
        mutableIntStateOf(0)
    }
    val progress by remember {
        derivedStateOf {
            currentQuestion.toFloat() / listVariants.size
        }
    }
    val listAnswers = rememberSaveable { mutableListOf<Int>() }
    fun addAnswerToList(answer: Int) {
        if (currentQuestion != 41) {
            listAnswers.add(answer)
            currentQuestion++
        } else {
            listAnswers.add(answer)
            navController.navigate("testResults/${Uri.encode(Gson().toJson(getTestResult(listAnswers)))}") {
                popUpTo(Destination.SelectTest.route)
            }
        }
    }
    Box(Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    color = ProfQuest2Theme.colors.primary,
                    trackColor = ProfQuest2Theme.colors.tertiary
                )
                Text(
                    text = stringResource(R.string.test_question),
                    color = ProfQuest2Theme.colors.onSurface
                )
                Text(
                    text = "Вопрос ${currentQuestion + 1}/${listVariants.size}",
                    color = ProfQuest2Theme.colors.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            ProfessionCard(listVariants[currentQuestion].first.image) {
                addAnswerToList(0)
            }
            Spacer(modifier = Modifier.height(16.dp))

            ProfessionCard(listVariants[currentQuestion].second.image) {
                addAnswerToList(1)
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        addAnswerToList(0)
                    },
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        bottomStart = 40.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(containerColor = ProfQuest2Theme.colors.primary),
                    modifier = Modifier
                        .weight(0.5f)
                        .height(64.dp)
                ) {
                    Text(
                        text = listVariants[currentQuestion].first.name,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        addAnswerToList(1)
                    },
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 40.dp,
                        bottomEnd = 40.dp
                    ),
                    colors = ButtonDefaults.buttonColors(containerColor = ProfQuest2Theme.colors.tertiary),
                    modifier = Modifier
                        .weight(0.5f)
                        .height(64.dp)
                ) {
                    Text(
                        text = listVariants[currentQuestion].second.name,
                        color = ProfQuest2Theme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Icon(
            icon = R.drawable.ic_arrow_left,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionCard(image: Int, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.size(height = 305.dp, width = 235.dp),
            contentScale = ContentScale.Crop
        )
    }
}