package flow.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import flow.data.api.repository.BookmarksRepository
import flow.data.api.repository.FavoritesRepository
import flow.data.api.repository.SearchHistoryRepository
import flow.data.api.repository.SettingsRepository
import flow.data.api.repository.SuggestsRepository
import flow.data.api.repository.VisitedRepository
import flow.data.api.service.FavoritesService
import flow.data.api.service.ForumService
import flow.data.api.service.SearchService
import flow.data.api.service.TopicService
import flow.data.api.service.TorrentService
import flow.data.impl.repository.BookmarksRepositoryImpl
import flow.data.impl.repository.FavoritesRepositoryImpl
import flow.data.impl.repository.SearchHistoryRepositoryImpl
import flow.data.impl.repository.SettingsRepositoryImpl
import flow.data.impl.repository.SuggestsRepositoryImpl
import flow.data.impl.repository.VisitedRepositoryImpl
import flow.data.impl.service.FavoritesServiceImpl
import flow.data.impl.service.ForumServiceImpl
import flow.data.impl.service.SearchServiceImpl
import flow.data.impl.service.TopicServiceImpl
import flow.data.impl.service.TorrentServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    @Singleton
    fun bookmarksRepository(impl: BookmarksRepositoryImpl): BookmarksRepository

    @Binds
    @Singleton
    fun favoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    @Singleton
    fun favoritesService(impl: FavoritesServiceImpl): FavoritesService

    @Binds
    @Singleton
    fun forumService(impl: ForumServiceImpl): ForumService

    @Binds
    @Singleton
    fun searchHistoryRepository(impl: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Binds
    @Singleton
    fun searchService(impl: SearchServiceImpl): SearchService

    @Binds
    @Singleton
    fun settingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    fun suggestsRepository(impl: SuggestsRepositoryImpl): SuggestsRepository

    @Binds
    @Singleton
    fun topicService(impl: TopicServiceImpl): TopicService

    @Binds
    @Singleton
    fun torrentService(impl: TorrentServiceImpl): TorrentService

    @Binds
    @Singleton
    fun visitedRepository(impl: VisitedRepositoryImpl): VisitedRepository
}