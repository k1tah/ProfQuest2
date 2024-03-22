package com.example.profquest2.ui.home

import androidx.lifecycle.ViewModel
import com.example.data.repository.AuthRepository
import com.example.data.repository.PostRepository
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
    private val authRepository: AuthRepository
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {

    override val container: Container<HomeState, HomeSideEffect> =
        container(HomeState(posts = listOf()))

    init {
        getPosts()
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
        if (response.status == HttpStatusCode.OK) {
            postSideEffect(HomeSideEffect.Done)
            val posts = response.body<List<Post>>()
            reduce { state.copy(posts = posts) }
        } else {
            postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
        }
    }

    fun like(postId: Long) = intent {
        val response = postRepository.like(postId, authRepository.getAuthToken())
        if (response.status == HttpStatusCode.OK) {
            val likedPost = response.body<Post>()
            reduce { state.copy(posts = state.posts.update(likedPost)) }
        } else {
            postSideEffect(HomeSideEffect.Error(response.status.value.toString()))
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
    val posts: List<Post>
)

sealed class HomeSideEffect {
    data class Error(val message: String) : HomeSideEffect()

    data object Done : HomeSideEffect()

    data object Loading : HomeSideEffect()
}