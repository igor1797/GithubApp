package igor.kuridza.dice.githubapp.repositories

import igor.kuridza.dice.githubapp.networking.GithubApiService

class SearchRepository(
    private val apiService: GithubApiService
) {
    suspend fun getRepositoriesBySearchQuery(searchQuery: String) = apiService.searchRepositoriesByQuery(searchQuery)

    suspend fun getUsersBySearchQuery(searchQuery: String) = apiService.searchUsersByQuery(searchQuery)
}