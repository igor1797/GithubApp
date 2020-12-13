package igor.kuridza.dice.githubapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.model.UsersResponse
import igor.kuridza.dice.githubapp.networking.GithubApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(
    private val apiService: GithubApiService
) {

    fun getRepositoriesBySearchQuery(searchQuery: String): LiveData<List<Repository>>{
        val repositories = MutableLiveData<List<Repository>>()
        apiService.searchRepositoriesByQuery(searchQuery).enqueue(object : Callback<RepositoriesResponse>{
            override fun onResponse(
                call: Call<RepositoriesResponse>,
                response: Response<RepositoriesResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        repositories.value = it.repositories
                    }
                }
            }

            override fun onFailure(call: Call<RepositoriesResponse>, t: Throwable) {}
        })
        return repositories
    }

    fun getUsersBySearchQuery(searchQuery: String): LiveData<List<User>>{
        val mUserList = MutableLiveData<List<User>>()
        apiService.searchUsersByQuery(searchQuery).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        mUserList.value = it.users
                    }
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {}
        })
        return mUserList
    }
}