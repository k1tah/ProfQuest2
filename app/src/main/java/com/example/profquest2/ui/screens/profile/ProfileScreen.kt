package com.example.profquest2.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.data.api.BASE_URL
import com.example.profquest2.R
import com.example.profquest2.extensions.replaceAll
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.PrimaryTextField
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.getTestResults()
    }

    var isEditMode by rememberSaveable {
        mutableStateOf(false)
    }

    val state = viewModel.collectAsState().value

    var fullname by rememberSaveable(state.profile?.name) {
        mutableStateOf(state.profile?.name ?: "")
    }
    var fileName by rememberSaveable(state.profile?.file) {
        mutableStateOf(state.profile?.file?.name ?: "")
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val resumePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            fileUri = it
            fileName = it?.path?.let { path -> File(path).name }.toString()
        }
    val photoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            photoUri = it
        }

    viewModel.collectSideEffect {
        isLoading = when (it) {
            is ProfileSideEffect.NotAuthorized -> {
                navController.replaceAll(Destination.Auth.route)
                false
            }

            is ProfileSideEffect.Error -> {
                context.showShortToast(it.message)
                false
            }

            is ProfileSideEffect.Loading -> true

            is ProfileSideEffect.Done -> false
        }
    }

    if (isLoading) {
        BasicAlertDialog(onDismissRequest = { }, modifier = Modifier.size(64.dp)) {
            CircularProgressIndicator(color = ProfQuest2Theme.colors.primary)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.weight(1f))
            TitleText(text = stringResource(id = R.string.profile))
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                icon = R.drawable.ic_settings,
                modifier = Modifier.clickable { navController.navigate(Destination.Settings.route) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.size(128.dp)) {
            SubcomposeAsyncImage(
                model = if (photoUri != null) photoUri else BASE_URL + "file/${state.profile?.photo?.id}",
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .align(Alignment.Center)
                    .size(128.dp)
                    .clickable { photoPickerLauncher.launch("image/*") },
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(color = ProfQuest2Theme.colors.primary)
                },
                error = {
                    androidx.compose.material3.Icon(
                        painter = rememberVectorPainter(image = Icons.Default.AccountCircle),
                        contentDescription = null,
                        tint = ProfQuest2Theme.colors.tertiary
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = if (isEditMode) fullname else state.profile?.name ?: "",
            onValueChange = { fullname = it },
            hint = stringResource(id = R.string.fullname),
            label = stringResource(id = R.string.fullname),
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = if (isEditMode) fileName else state.profile?.file?.name ?: "",
            onValueChange = { },
            hint = stringResource(id = R.string.select_document),
            label = stringResource(id = R.string.resume),
            trailingIcon = {
                Icon(
                    icon = R.drawable.ic_add_doc,
                    tint = if (isEditMode) ProfQuest2Theme.colors.primary else ProfQuest2Theme.colors.tertiary,
                    modifier = Modifier.clickable {
                        resumePickerLauncher.launch("*/*")
                    }
                )
            },
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        SubtitleText(text = stringResource(id = R.string.test_results))
        Spacer(modifier = Modifier.height(16.dp))

        TitleText(text = stringResource(id = R.string.golland_test))
        Spacer(modifier = Modifier.height(8.dp))
        if (!state.testResults.first.isNullOrBlank()) {
            BodyText(text = state.testResults.first!!)
        } else {
            PrimaryButton(
                onClick = { navController.replaceAll(Destination.Test.route) },
                text = stringResource(id = R.string.go_to_test)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TitleText(text = stringResource(id = R.string.second_test))
        Spacer(modifier = Modifier.height(8.dp))
        if (!state.testResults.second.isNullOrBlank()) {
            BodyText(text = state.testResults.second!!)
        } else {
            PrimaryButton(
                onClick = { navController.replaceAll(Destination.SecondTest.route) },
                text = stringResource(id = R.string.go_to_test)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            onClick = {
                isEditMode = if (isEditMode) {
                    viewModel.updateProfile(
                        fullname,
                        fileUri?.let { context.contentResolver.openInputStream(it) },
                        fileUri?.path?.let { File(it).name },
                        photoUri?.let { context.contentResolver.openInputStream(it) }
                    )
                    false
                } else {
                    true
                }
            },
            text = if (isEditMode) stringResource(id = R.string.done) else stringResource(id = R.string.edit_profile),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}