package com.example.profquest2.ui.screens.company

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.composables.button.PrimaryRadioButton
import com.example.profquest2.ui.composables.dialog.LoadingDialog
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.item.CourseItem
import com.example.profquest2.ui.composables.item.PracticeItem
import com.example.profquest2.ui.composables.item.VacancyItem
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.screens.vacancies.VacanciesSideEffect
import com.example.profquest2.ui.screens.vacancies.VacanciesViewModel
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CompanyVacanciesScreen(
    id: Long,
    navController: NavController,
    viewModel: VacanciesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getCompanyVacancies(id)
    }

    var unauthorized by rememberSaveable {
        mutableStateOf(false)
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is VacanciesSideEffect.Unauthorized -> {
                unauthorized = true
                isLoading = false
            }

            is VacanciesSideEffect.Loading -> isLoading = true

            is VacanciesSideEffect.ResumeSended -> context.showShortToast("Резюме отправлено!")

            is VacanciesSideEffect.Done -> {
                unauthorized = false
                isLoading = false
            }

            is VacanciesSideEffect.Error -> isLoading = false
        }
    }

    val state = viewModel.collectAsState().value

    if (isLoading) LoadingDialog(Modifier.fillMaxSize())

    val types = arrayOf(
        stringResource(id = R.string.vacancies),
        stringResource(id = R.string.curses),
        stringResource(id = R.string.practices)
    )

    var currentType by rememberSaveable { mutableStateOf("Вакансии") }

    LaunchedEffect(currentType) {
        when (currentType) {
            "Вакансии" -> viewModel.getCompanyVacancies(id)

            "Курсы" -> viewModel.getCompanyCourses(id)

            "Практики" -> viewModel.getCompanyPractices(id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon = R.drawable.ic_arrow_left,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            TitleText(text = "$currentType компании")
            Spacer(modifier = Modifier.width(16.dp))

            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(64.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            types.forEach {
                PrimaryRadioButton(
                    state = currentType == it, onClick = {
                        currentType = it
                    },
                    text = it
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (currentType) {
                "Вакансии" -> {
                    items(state.companyVacancies) { vacancy ->
                        VacancyItem(
                            vacancy,
                            onFavouriteClick = {
                                viewModel.updateIsFavourite(vacancy.id)
                            },
                            onSendResume = {
                                viewModel.sendResume(vacancy.id)
                            }
                        )
                    }
                }

                "Курсы" -> {
                    items(state.companyCourses) { course ->
                        CourseItem(course)
                    }
                }

                "Практики" -> {
                    items(state.companyPractices) { practice ->
                        PracticeItem(practice = practice)
                    }
                }
            }
        }
    }
}