package igor.kuridza.dice.githubapp.networking

import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun searchRepositoriesByQuery(@Query("q") query: String): Response<RepositoriesResponse>

    @GET("search/users")
    suspend fun searchUsersByQuery(@Query("q") query: String): Response<UsersResponse>
}