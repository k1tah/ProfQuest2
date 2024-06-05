package com.example.profquest2.ui.screens.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profquest2.ui.composables.item.VacancyItem
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.screens.vacancies.VacanciesSideEffect
import com.example.profquest2.ui.screens.vacancies.VacanciesViewModel
import com.example.profquest2.utils.OnBottomReached
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun CompanyContentScreen(type: String, viewModel: VacanciesViewModel = hiltViewModel()){
//   coursesState: /*TODO*/, practicesState: /*TODO*/

    val context = LocalContext.current

    var unauthorized by rememberSaveable {
        mutableStateOf(false)
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    var currentPage by rememberSaveable {
        mutableIntStateOf(0)
    }
    val scrollState = rememberLazyListState()
    scrollState.OnBottomReached {
        viewModel.getVacancies(
            page = ++currentPage,
            query = ""
        )
    }

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

            is VacanciesSideEffect.Error -> {
                isLoading = false
                context.showShortToast(it.message)
            }
        }
    }
    val vacanciesState = viewModel.collectAsState().value
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(text = "Company" /*Название компании*/)
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (type) {
                "Vacancies" -> {
                    items(vacanciesState.vacancies) { vacancy ->
                        vacanciesState.companies.find { it.id == vacancy.company }
                            ?.let { company ->
                                VacancyItem(
                                    vacancy,
                                    company,
                                    onFavouriteClick = {
                                        viewModel.updateIsFavourite(vacancy.id)
                                    },
                                    onSendResume = {
                                        viewModel.sendResume(vacancy.id)
                                    }
                                )
                            }
                    }
                }
                "Courses" -> {
                    //    items(state.vacancies) { vacancy ->
//        state.companies.find { it.id == vacancy.company }
//            ?.let { company ->
//                VacancyItem(
//                    vacancy,
//                    company,
//                    onFavouriteClick = {
//                        viewModel.updateIsFavourite(vacancy.id)
//                    },
//                    onSendResume = {
//                        viewModel.sendResume(vacancy.id)
//                    }
//                )
//            }
//    }
                }

                "Practices" -> {
                    //    items(state.vacancies) { vacancy ->
//        state.companies.find { it.id == vacancy.company }
//            ?.let { company ->
//                VacancyItem(
//                    vacancy,
//                    company,
//                    onFavouriteClick = {
//                        viewModel.updateIsFavourite(vacancy.id)
//                    },
//                    onSendResume = {
//                        viewModel.sendResume(vacancy.id)
//                    }
//                )
//            }
//    }
                }
            }
        }
    }
}
