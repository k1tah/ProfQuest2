package com.example.profquest2.ui.screens.schools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.model.School
import com.example.profquest2.R
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.post.PostIcon
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.textField.SearchField
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SchoolsScreen(navController: NavController, viewModel: SchoolsViewModel = hiltViewModel()) {
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val state = viewModel.collectAsState().value

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
        LaunchedEffect(Unit) { viewModel.getSchools() }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.schools.filter { it.name.contains(searchQuery, ignoreCase = true) }) { company ->
                SchoolItem(
                    company,
                    onCompanyClick = {
                        navController.navigate(Destination.School.route + "/${company.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun SchoolItem(school: School, onCompanyClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCompanyClick() }
    ) {
        PostIcon(fileId = school.image?.id.toString())
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            SubtitleText(text = school.name)
            Spacer(modifier = Modifier.height(2.dp))
            LabelText(text = school.email)
        }
    }
}