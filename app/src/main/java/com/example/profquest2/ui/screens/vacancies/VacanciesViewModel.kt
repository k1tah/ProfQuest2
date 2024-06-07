package com.example.profquest2.ui.screens.vacancies

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.VacanciesRepository
import com.example.domain.model.Company
import com.example.domain.model.Course
import com.example.domain.model.Practice
import com.example.domain.model.Vacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class VacanciesViewModel @Inject constructor(
    private val vacanciesRepository: VacanciesRepository,
    private val authRepository: AuthRepository,
    private val companyRepository: CompanyRepository
) : ContainerHost<VacanciesState, VacanciesSideEffect>, ViewModel() {

    override val container: Container<VacanciesState, VacanciesSideEffect> = container(VacanciesState())

    private var delayJob: Job? = null

    init {
        getCompanies()
        getVacancies()
    }

    fun refreshVacancies(query: String = "") = intent {
        val response = vacanciesRepository.getVacancies(
            query,
            "",
            "",
            0,
            10,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val vacancies = response.body<List<Vacancy>>()
                reduce { state.copy(vacancies = vacancies) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun refreshPractices(query: String = "") = intent {
        val response = vacanciesRepository.getPractices(
            query,
            "",
            "",
            0,
            10,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val practices = response.body<List<Practice>>()
                reduce { state.copy(practices = practices) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun refreshCourses(query: String = "") = intent {
        val response = vacanciesRepository.getCourses(
            query,
            "",
            "",
            0,
            10,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val courses = response.body<List<Course>>()
                reduce { state.copy(courses = courses) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getVacancies(
        query: String = "",
        address: String = "",
        name: String = "",
        page: Int = 0,
        size: Int = 10
    ) = intent {
        val response = vacanciesRepository.getVacancies(
            query,
            address,
            name,
            page,
            size,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val vacancies = response.body<List<Vacancy>>()
                val currentVacancies = state.vacancies.toMutableList()
                currentVacancies.addAll(vacancies)
                reduce { state.copy(vacancies = currentVacancies) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getPractices(
        query: String = "",
        address: String = "",
        name: String = "",
        page: Int = 0,
        size: Int = 10
    ) = intent {
        val response = vacanciesRepository.getPractices(
            query,
            address,
            name,
            page,
            size,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val practices = response.body<List<Practice>>()
                val currentVacancies = state.practices.toMutableList()
                currentVacancies.addAll(practices)
                reduce { state.copy(practices = currentVacancies) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCourses(
        query: String = "",
        address: String = "",
        name: String = "",
        page: Int = 0,
        size: Int = 10
    ) = intent {
        val response = vacanciesRepository.getCourses(
            query,
            address,
            name,
            page,
            size,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(VacanciesSideEffect.Done)
                val courses = response.body<List<Course>>()
                val currentCourses = state.courses.toMutableList()
                currentCourses.addAll(courses)
                reduce { state.copy(courses = currentCourses) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun searchCourses(query: String) {
        if (delayJob?.isActive == true) delayJob?.cancel()
        delayJob = CoroutineScope(Dispatchers.IO).launch {
            delay(300L)
            refreshCourses(query = query)
        }
    }

    fun searchPractices(query: String) {
        if (delayJob?.isActive == true) delayJob?.cancel()
        delayJob = CoroutineScope(Dispatchers.IO).launch {
            delay(300L)
            refreshPractices(query = query)
        }
    }

    fun searchVacancies(query: String) {
        if (delayJob?.isActive == true) delayJob?.cancel()
        delayJob = CoroutineScope(Dispatchers.IO).launch {
            delay(300L)
            refreshVacancies(query = query)
        }
    }

    fun sendResume(id: Long) = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = vacanciesRepository.sendResume(id, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> postSideEffect(VacanciesSideEffect.ResumeSended)

            HttpStatusCode.Continue -> postSideEffect(VacanciesSideEffect.ResumeSended)

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            HttpStatusCode.NotFound -> postSideEffect(VacanciesSideEffect.ResumeNotFound)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getFavouritesVacancies() = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = vacanciesRepository.getFavouritesVacancies(authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val vacancies = response.body<List<Vacancy>>()
                reduce { state.copy(favouritesVacancies = vacancies) }
                postSideEffect(VacanciesSideEffect.Done)
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCompanyVacancies(id: Long) = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = vacanciesRepository.getCompanyVacancies(id, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val vacancies = response.body<List<Vacancy>>()
                reduce { state.copy(companyVacancies = vacancies) }
                postSideEffect(VacanciesSideEffect.Done)
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCompanyPractices(
        id: Long
    ) = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = vacanciesRepository.getCompanyPractices(id, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val practices = response.body<List<Practice>>()
                reduce { state.copy(companyPractices = practices) }
                postSideEffect(VacanciesSideEffect.Done)
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCompanyCourses(
        id: Long
    ) = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = vacanciesRepository.getCompanyCourses(id, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val courses = response.body<List<Course>>()
                reduce { state.copy(companyCourses = courses) }
                postSideEffect(VacanciesSideEffect.Done)
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun updateIsFavourite(id: Long) = intent {
        val response = vacanciesRepository.updateIsFavourite(id, authRepository.getAuthToken())

        when (response.status) {
            HttpStatusCode.OK -> {
                val likedPost = response.body<Vacancy>()
                reduce { state.copy(vacancies = state.vacancies.update(likedPost)) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    private fun getCompanies() = intent {
        postSideEffect(VacanciesSideEffect.Loading)
        val response = companyRepository.getCompanies(authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val companies = response.body<List<Company>>()
                reduce { state.copy(companies = companies) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(VacanciesSideEffect.Unauthorized)

            else -> postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }
}

fun List<Vacancy>.update(item: Vacancy): List<Vacancy> {
    val itemIndex = indexOf(find { it.id == item.id })
    val newList = this.toMutableList()
    newList[itemIndex] = item
    return newList
}

data class VacanciesState(
    val vacancies: List<Vacancy> = listOf(),
    val companies: List<Company> = listOf(),
    val practices: List<Practice> = listOf(),
    val courses: List<Course> = listOf(),
    val favouritesVacancies: List<Vacancy> = listOf(),
    val companyVacancies: List<Vacancy> = listOf(),
    val companyPractices: List<Practice> = listOf(),
    val companyCourses: List<Course> = listOf()
)

sealed interface VacanciesSideEffect {
    data object Loading : VacanciesSideEffect

    data object Unauthorized : VacanciesSideEffect

    data class Error(val message: String) : VacanciesSideEffect

    data object Done : VacanciesSideEffect

    data object ResumeSended : VacanciesSideEffect

    data object ResumeNotFound : VacanciesSideEffect
}