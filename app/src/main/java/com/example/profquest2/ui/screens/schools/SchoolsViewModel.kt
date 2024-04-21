package com.example.profquest2.ui.screens.schools

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.PostRepository
import com.example.domain.model.Post
import com.example.domain.model.School
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
class SchoolsViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val authRepository: AuthRepository,
    private val postRepository: PostRepository
) : ContainerHost<SchoolsState, SchoolsSideEffect>, ViewModel() {

    override val container: Container<SchoolsState, SchoolsSideEffect> = container(SchoolsState())

    init {
        getSchools()
    }

    fun getSchools() = intent {
        postSideEffect(SchoolsSideEffect.Loading)
        val response = companyRepository.getSchools(authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(SchoolsSideEffect.Done)
            val companies = response.body<List<School>>()
            reduce { state.copy(schools = companies) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getSchool(id: Long) = intent {
        postSideEffect(SchoolsSideEffect.Loading)
        val response = companyRepository.getSchool(authRepository.getAuthToken(), id)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(SchoolsSideEffect.Done)
            val companies = response.body<School>()
            reduce { state.copy(school = companies) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getPosts(id: Long, page: Int = 0, size: Int = 10) = intent {
        postSideEffect(SchoolsSideEffect.Loading)
        val response = companyRepository.getCompanyPosts(authRepository.getAuthToken(), id, page, size)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(SchoolsSideEffect.Done)
            val posts = response.body<List<Post>>()
            val currentPosts = state.posts.toMutableList()
            currentPosts.addAll(posts)
            reduce { state.copy(posts = currentPosts) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun refreshPosts(id: Long) = intent {
        postSideEffect(SchoolsSideEffect.Loading)
        val response = companyRepository.getCompanyPosts(authRepository.getAuthToken(), id, 0, 10)
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(SchoolsSideEffect.Done)
            val posts = response.body<List<Post>>()
            reduce { state.copy(posts = posts) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun like(postId: Long) = intent {
        val response = postRepository.like(postId, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val likedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(likedPost)) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun vote(postId: Long, variant: Int) = intent {
        val response = postRepository.vote(postId, variant, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val votedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(votedPost)) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }

    fun undoVote(postId: Long) = intent {
        val response = postRepository.undoVote(postId, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val votedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(votedPost)) }
        } else {
            postSideEffect(SchoolsSideEffect.Error(response.status.value.toString()))
        }
    }
}

data class SchoolsState(
    val posts: List<Post> = listOf(),
    val schools: List<School> = listOf(),
    val school: School? = null
)

sealed class SchoolsSideEffect {
    data class Error(val message: String) : SchoolsSideEffect()

    data object Done : SchoolsSideEffect()

    data object Loading : SchoolsSideEffect()
}