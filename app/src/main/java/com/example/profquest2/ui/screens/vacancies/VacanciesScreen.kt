package com.example.profquest2.ui.screens.vacancies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.profquest2.R
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.button.PrimaryRadioButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.item.VacancyItem
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.SearchField
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.utils.OnBottomReached
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun VacanciesScreen(navController: NavController, viewModel: VacanciesViewModel = hiltViewModel()) {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var unauthorized by rememberSaveable {
        mutableStateOf(false)
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }
    var vacanciesState by rememberSaveable{ mutableStateOf(true) }
    var coursesState by rememberSaveable{ mutableStateOf(false) }
    var practicesState by rememberSaveable{ mutableStateOf(false) }
    var currentInfoType by rememberSaveable { mutableStateOf("Vacancies") }

    var currentPage by rememberSaveable {
        mutableIntStateOf(0)
    }

    val scrollState = rememberLazyListState()
    scrollState.OnBottomReached {
        viewModel.getVacancies(
            page = ++currentPage,
            query = if (isSearchVisible) searchQuery else ""
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
    when(currentInfoType){
        "Vacancies" ->{
            /*TODO*/
        }
        "Curses" ->{
            /*TODO*/
        }
        "Practices" ->{
            /*TODO*/
        }
    }
    val state = viewModel.collectAsState().value

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
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(64.dp)
            ) {
                AnimatedVisibility(visible = !isSearchVisible) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            icon = R.drawable.ic_favorites,
                            modifier = Modifier.clickable { navController.navigate(Destination.FavouritesVacancies.route) }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            icon = R.drawable.ic_school,
                            modifier = Modifier.clickable { navController.navigate(Destination.Schools.route) }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TitleText(text = stringResource(id = R.string.vacansies))
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
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
                        onValueChanged = {
                            searchQuery = it
                            if (searchQuery.length >= 2) viewModel.searchVacancies(searchQuery)
                        },
                        onClose = {
                            isSearchVisible = false
                            viewModel.getVacancies()
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(64.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                PrimaryRadioButton(state = vacanciesState, onClick = {
                    currentInfoType = "Vacancies"
                    vacanciesState = true
                    coursesState = false
                    practicesState = false},
                    text = stringResource(id = R.string.vacancies))
                PrimaryRadioButton(state = coursesState, onClick = {
                    currentInfoType = "Curses"
                    vacanciesState = false
                    coursesState = true
                    practicesState = false},
                    text = stringResource(id = R.string.curses))
                PrimaryRadioButton(state = practicesState, onClick = {
                    currentInfoType = "Practices"
                    vacanciesState = false
                    coursesState = false
                    practicesState = true},
                    text = stringResource(id = R.string.practices))
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.vacancies) { vacancy ->
                    state.companies.find { it.id == vacancy.company }
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
        }
    }
}

