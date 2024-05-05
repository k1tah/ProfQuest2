package com.example.profquest2.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.CompanyRepository
import com.example.data.repository.PostRepository
import com.example.domain.model.Company
import com.example.domain.model.Post
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
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val companyRepository: CompanyRepository,
    private val authRepository: AuthRepository
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> =
        container(HomeState(posts = listOf()))

    init {
        getPosts()
    }

    fun refreshPosts() = intent {
        postSideEffect(HomeSideEffect.Loading)
        val response = postRepository.getPosts("", "", "", 0, 10, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(HomeSideEffect.Done)
                val posts = response.body<List<Post>>()
                reduce { state.copy(posts = posts) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getPosts(
        search: String = "",
        company: String = "",
        address: String = "",
        page: Int = 0,
        size: Int = 10
    ) = intent {
        postSideEffect(HomeSideEffect.Loading)
        val response = postRepository.getPosts(
            search,
            company,
            address,
            page,
            size,
            authRepository.getAuthToken()
        )
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(HomeSideEffect.Done)
                val posts = response.body<List<Post>>()
                val currentPosts = state.posts.toMutableList()
                currentPosts.addAll(posts)
                reduce { state.copy(posts = currentPosts) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun getCompanies() = intent {
        postSideEffect(HomeSideEffect.Loading)
        val response = companyRepository.getCompanies(authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                postSideEffect(HomeSideEffect.Done)
                val companies = response.body<List<Company>>()
                reduce { state.copy(companies = companies) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun like(postId: Long) = intent {
        val response = postRepository.like(postId, authRepository.getAuthToken())

        when (response.status) {
            HttpStatusCode.OK -> {
                val likedPost = response.body<Post>()
                reduce { state.copy(posts = state.posts.update(likedPost)) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun vote(postId: Long, variant: Int) = intent {
        val response = postRepository.vote(postId, variant, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val votedPost = response.body<Post>()
                reduce { state.copy(posts = state.posts.update(votedPost)) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun undoVote(postId: Long) = intent {
        val response = postRepository.undoVote(postId, authRepository.getAuthToken())
        when (response.status) {
            HttpStatusCode.OK -> {
                val votedPost = response.body<Post>()
                reduce { state.copy(posts = state.posts.update(votedPost)) }
            }

            HttpStatusCode.Unauthorized -> postSideEffect(HomeSideEffect.Unauthorized)

            else -> postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }
}

fun List<Post>.update(item: Post): List<Post> {
    val itemIndex = indexOf(find { it.id == item.id })
    val newList = this.toMutableList()
    newList[itemIndex] = item
    return newList
}

data class HomeState(
    val posts: List<Post> = listOf(),
    val companies: List<Company> = listOf()
)

sealed interface HomeSideEffect {
    data object Unauthorized : HomeSideEffect

    data class Error(val message: String) : HomeSideEffect

    data object Done : HomeSideEffect

    data object Loading : HomeSideEffect
}