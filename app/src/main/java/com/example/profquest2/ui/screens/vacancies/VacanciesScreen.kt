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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.domain.model.Company
import com.example.domain.model.Vacancy
import com.example.profquest2.R
import com.example.profquest2.extensions.formatDate
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.post.PostIcon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.SearchField
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
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

    viewModel.collectSideEffect {
        when (it) {
            is VacanciesSideEffect.Unauthorized -> {
                unauthorized = true
                isLoading = false
            }

            is VacanciesSideEffect.Loading -> isLoading = true


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
                        onValueChanged = { searchQuery = it },
                        onClose = { isSearchVisible = false }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.vacancies) { vacancy ->
                    state.companies.find { it.id == vacancy.company }
                        ?.let {
                            VacancyItem(vacancy, it) {
                                viewModel.updateIsFavourite(vacancy.id)
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun VacancyItem(vacancy: Vacancy, company: Company, onFavouriteClick: () -> Unit) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PostIcon(fileId = company.image?.id.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleText(text = company.name)
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = vacancy.date.formatDate() ?: "")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleText(text = vacancy.vacancyName)
            Spacer(modifier = Modifier.height(4.dp))

            Spacer(modifier = Modifier.height(16.dp))
            if (!expanded) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LabelText(
                        text = stringResource(R.string.show_more),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        icon = if (vacancy.isFavourite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                        modifier = Modifier.clickable { onFavouriteClick() }
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    SubtitleText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = vacancy.description)
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.contacts))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = vacancy.email)
                    Spacer(modifier = Modifier.height(8.dp))

                    LabelText(
                        text = stringResource(R.string.hide),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LabelText(
                            text = stringResource(R.string.show_more),
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            icon = if (vacancy.isFavourite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                            modifier = Modifier.clickable { onFavouriteClick() }
                        )
                    }
                }
            }
        }
    }
}