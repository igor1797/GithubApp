package igor.kuridza.dice.githubapp.repositories

import igor.kuridza.dice.githubapp.common.makeNetworkRequest
import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.UsersResponse
import igor.kuridza.dice.githubapp.networking.GithubApiService
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val apiService: GithubApiService
) {
    suspend fun getRepositoriesBySearchQuery(searchQuery: String): Flow<Resource<RepositoriesResponse>> {
        return makeNetworkRequest(searchQuery) {
            apiService.searchRepositoriesByQuery(it)
        }
    }

    suspend fun getUsersBySearchQuery(searchQuery: String): Flow<Resource<UsersResponse>> {
        return makeNetworkRequest(searchQuery) {
            apiService.searchUsersByQuery(it)
        }
    }
}