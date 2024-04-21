package com.example.profquest2.ui.screens.company

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.PostRepository
import com.example.domain.model.Company
import com.example.domain.model.Post
import com.example.profquest2.ui.screens.home.update
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
class CompanyViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val companyRepository: CompanyRepository,
    private val authRepository: AuthRepository
) : ContainerHost<CompanyState, CompanySideEffect>, ViewModel() {
    override val container: Container<CompanyState, CompanySideEffect> =
        container(CompanyState(company = null))

    fun getCompany(id: Long) = intent {
        postSideEffect(CompanySideEffect.Loading)
        val response = companyRepository.getCompany(authRepository.getAuthToken(), id)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(CompanySideEffect.Done)
            val company = response.body<Company>()
            reduce { state.copy(company = company) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
        getPosts(id)
    }

    fun getPosts(id: Long, page: Int = 0, size: Int = 10) = intent {
        postSideEffect(CompanySideEffect.Loading)
        val response = companyRepository.getCompanyPosts(authRepository.getAuthToken(), id, page, size)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(CompanySideEffect.Done)
            val posts = response.body<List<Post>>()
            val currentPosts = state.posts.toMutableList()
            currentPosts.addAll(posts)
            reduce { state.copy(posts = currentPosts) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
    }

    fun refreshPosts(id: Long) = intent {
        postSideEffect(CompanySideEffect.Loading)
        val response = companyRepository.getCompanyPosts(authRepository.getAuthToken(), id, 0, 10)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(CompanySideEffect.Done)
            val posts = response.body<List<Post>>()
            reduce { state.copy(posts = posts) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
    }

    fun like(postId: Long) = intent {
        val response = postRepository.like(postId, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val likedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(likedPost)) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
    }

    fun vote(postId: Long, variant: Int) = intent {
        val response = postRepository.vote(postId, variant, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val votedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(votedPost)) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
    }

    fun undoVote(postId: Long) = intent {
        val response = postRepository.undoVote(postId, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val votedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(votedPost)) }
        } else {
            postSideEffect(CompanySideEffect.Error(response.status.value.toString()))
        }
    }
}

data class CompanyState(
    val company: Company? = null,
    val posts: List<Post> = listOf()
)

sealed class CompanySideEffect {
    data class Error(val message: String) : CompanySideEffect()

    data object Done : CompanySideEffect()

    data object Loading : CompanySideEffect()
}