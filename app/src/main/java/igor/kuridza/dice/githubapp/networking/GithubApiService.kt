package igor.kuridza.dice.githubapp.networking

import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    fun searchRepositoriesByQuery(@Query("q") query: String): Call<RepositoriesResponse>

    @GET("search/users")
    fun searchUsersByQuery(@Query("q") query: String): Call<UsersResponse>
}