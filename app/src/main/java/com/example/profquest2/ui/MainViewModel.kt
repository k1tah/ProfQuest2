package com.example.profquest2.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val authRepository: AuthRepository
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container: Container<MainState, MainSideEffect> =
        container(MainState(settingsRepository.getDarkTheme()))

    fun setTheme(isDarkTheme: Boolean) = intent {
        settingsRepository.setDarkTheme(isDarkTheme)
        reduce { state.copy(isDarkTheme = isDarkTheme) }
    }

    fun getUserId() = authRepository.getUserId()
}

data class MainState(
    val isDarkTheme: Boolean = false
)

sealed class MainSideEffect