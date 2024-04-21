package com.example.profquest2.ui.screens.vacancies

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.VacanciesRepository
import com.example.domain.model.Company
import com.example.domain.model.Vacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
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

    override val container: Container<VacanciesState, VacanciesSideEffect> =
        container(VacanciesState())

    init {
        getCompanies()
        getVacancies()
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
        if (response.status == HttpStatusCode.OK) {
            val vacancies = response.body<List<Vacancy>>()
            reduce { state.copy(vacancies = vacancies) }
        } else {
            postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCompanies() = intent {
        val response = companyRepository.getCompanies(authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val companies = response.body<List<Company>>()
            reduce { state.copy(companies = companies) }
        } else {
            postSideEffect(VacanciesSideEffect.Error(response.status.value.toString()))
        }
    }
}

data class VacanciesState(
    val vacancies: List<Vacancy> = listOf(),
    val companies: List<Company> = listOf()
)

sealed class VacanciesSideEffect {
    data object Loading : VacanciesSideEffect()

    data object NotAuthorized : VacanciesSideEffect()

    data class Error(val message: String) : VacanciesSideEffect()

    data object Done : VacanciesSideEffect()
}