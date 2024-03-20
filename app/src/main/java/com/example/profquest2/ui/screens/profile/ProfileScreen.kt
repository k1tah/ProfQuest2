package com.example.profquest2.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.button.PrimaryButton
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.SubtitleText
import com.example.profquest2.ui.view.text.TitleText
import com.example.profquest2.ui.view.textField.PrimaryTextField
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.io.File

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    var isEditMode by rememberSaveable {
        mutableStateOf(false)
    }

    val state = viewModel.collectAsState().value.profile
    var fullname by rememberSaveable(state?.name) {
        mutableStateOf(state?.name ?: "")
    }
    var photo by rememberSaveable(state?.photo) {
        mutableStateOf(state?.photo ?: "")
    }
    var fileName by rememberSaveable(state?.file) {
        mutableStateOf(state?.file?.name ?: "")
    }
    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            fileUri = it
        }

    viewModel.collectSideEffect {
        when (it) {
            is ProfileSideEffect.NotAuthorized -> context.showShortToast("Не авторизован")

            is ProfileSideEffect.Error -> context.showShortToast(it.message)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .align(Alignment.Center)
                    .size(128.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = if (isEditMode) fullname else state?.name ?: "",
            onValueChange = { fullname = it },
            hint = stringResource(id = R.string.fullname),
            label = stringResource(id = R.string.fullname),
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = if (isEditMode) photo else state?.photo ?: "",
            onValueChange = { photo = it },
            hint = stringResource(id = R.string.photo),
            label = stringResource(id = R.string.photo),
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            value = if (isEditMode) fileName else state?.file?.name ?: "",
            onValueChange = { },
            hint = stringResource(id = R.string.select_document),
            label = stringResource(id = R.string.resume),
            trailingIcon = {
                Icon(
                    icon = R.drawable.ic_add_doc,
                    tint = if (isEditMode) ProfQuest2Theme.colors.primary else ProfQuest2Theme.colors.tertiary,
                    modifier = Modifier.clickable {
                        launcher.launch("*/*")
                    }
                )
            },
            enabled = isEditMode,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        SubtitleText(text = stringResource(id = R.string.test_results))
        Spacer(modifier = Modifier.height(16.dp))

        BodyText(text = "Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст Очень большой текст")

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            onClick = {
                isEditMode = if (isEditMode) {
                    viewModel.updateProfile(
                        fullname,
                        photo,
                        fileUri?.let { context.contentResolver.openInputStream(it) },
                        fileUri?.path?.let { File(it).name }
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
    }
}