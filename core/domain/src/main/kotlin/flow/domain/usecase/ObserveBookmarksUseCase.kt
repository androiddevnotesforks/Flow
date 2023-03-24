package flow.domain.usecase

import flow.data.api.repository.BookmarksRepository
import flow.models.forum.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBookmarksUseCase @Inject constructor(
    private val repository: BookmarksRepository,
) {
    operator fun invoke(): Flow<List<CategoryModel>> {
        return repository.observeBookmarks()
    }
}