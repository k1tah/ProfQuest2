package com.example.profquest2.ui.screens.test

import androidx.lifecycle.ViewModel
import com.example.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TestsViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ContainerHost<TestsState, TestsSideEffect>, ViewModel() {

    override val container: Container<TestsState, TestsSideEffect> = container(TestsState())

    fun setGollandTestResult(result: String) = profileRepository.setGollandTestResult(result)

    fun setYovayshiTestResult(result: String) = profileRepository.setYovayshiTestResult(result)
}

class TestsState

sealed class TestsSideEffect