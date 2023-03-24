package flow.visited

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import flow.domain.usecase.EnrichTopicsUseCase
import flow.domain.usecase.ObserveVisitedUseCase
import flow.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class VisitedViewModel @Inject constructor(
    private val enrichTopicsUseCase: EnrichTopicsUseCase,
    private val observeVisitedUseCase: ObserveVisitedUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel(), ContainerHost<VisitedState, VisitedSideEffect> {
    override val container: Container<VisitedState, VisitedSideEffect> = container(
        initialState = VisitedState.Initial,
        onCreate = { observeVisited() },
    )

    fun perform(action: VisitedAction) {
        when (action) {
            is VisitedAction.FavoriteClick -> viewModelScope.launch { toggleFavoriteUseCase(action.topicModel) }
            is VisitedAction.TopicClick -> intent { postSideEffect(VisitedSideEffect.OpenTopic(action.topic)) }
            is VisitedAction.TorrentClick -> intent { postSideEffect(VisitedSideEffect.OpenTorrent(action.torrent)) }
        }
    }

    private fun observeVisited() {
        viewModelScope.launch {
            observeVisitedUseCase()
                .flatMapLatest(enrichTopicsUseCase::invoke)
                .map { items ->
                    if (items.isEmpty()) {
                        VisitedState.Empty
                    } else {
                        VisitedState.VisitedList(items)
                    }
                }
                .collectLatest { state -> intent { reduce { state } } }
        }
    }
}