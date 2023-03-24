package flow.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import flow.common.launchCatching
import flow.domain.usecase.GetForumUseCase
import flow.models.forum.Category
import flow.models.forum.RootCategory
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class ForumViewModel @Inject constructor(
    private val getForumUseCase: GetForumUseCase,
) : ViewModel(), ContainerHost<ForumState, ForumSideEffect> {
    override val container: Container<ForumState, ForumSideEffect> = container(
        initialState = ForumState.Loading,
        onCreate = { loadForum() },
    )

    fun perform(action: ForumAction) {
        when (action) {
            is ForumAction.CategoryClick -> onCategoryClick(action.category)
            is ForumAction.ExpandClick -> onExpandClick(action.expandable)
            is ForumAction.RetryClick -> loadForum()
        }
    }

    private fun loadForum() = intent {
        reduce { ForumState.Loading }
        viewModelScope.launchCatching(
            onFailure = { reduce { ForumState.Error(it) } }
        ) {
            val forum = getForumUseCase()
            reduce { ForumState.Loaded(forum.children.map(::Expandable)) }
        }
    }

    private fun onCategoryClick(category: Category) = intent {
        postSideEffect(ForumSideEffect.OpenCategory(category))
    }

    private fun onExpandClick(value: Expandable<RootCategory>) = intent {
        (state as? ForumState.Loaded)?.let { state ->
            reduce {
                state.copy(
                    forum = state.forum.map { expandable ->
                        if (expandable.item.name == value.item.name) {
                            expandable.copy(expanded = !expandable.expanded)
                        } else {
                            expandable
                        }
                    },
                )
            }
        }
    }
}