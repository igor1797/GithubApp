package igor.kuridza.dice.githubapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import igor.kuridza.dice.githubapp.model.*
import igor.kuridza.dice.githubapp.networking.GithubApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(
    private val apiService: GithubApiService
) {

    fun getRepositoriesBySearchQuery(searchQuery: String): LiveData<Resource<List<Repository>>>{
        val repositories = MutableLiveData<Resource<List<Repository>>>()
        repositories.postValue(Resource.Loading())
        apiService.searchRepositoriesByQuery(searchQuery).enqueue(object : Callback<RepositoriesResponse>{
            override fun onResponse(
                call: Call<RepositoriesResponse>,
                response: Response<RepositoriesResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        repositories.postValue(Resource.Success(it.repositories))
                    }
                }else
                    repositories.postValue(Resource.Error(response.message()))
            }

            override fun onFailure(call: Call<RepositoriesResponse>, t: Throwable) {
                repositories.postValue(Resource.Error(t.message))
            }
        })
        return repositories
    }

    fun getUsersBySearchQuery(searchQuery: String): LiveData<Resource<List<User>>>{
        val mUserList = MutableLiveData<Resource<List<User>>>()
        mUserList.postValue(Resource.Loading())
        apiService.searchUsersByQuery(searchQuery).enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        mUserList.postValue(Resource.Success(it.users))
                    }
                }else
                    mUserList.postValue(Resource.Error(response.message()))
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                mUserList.postValue(Resource.Error(t.message))
            }
        })
        return mUserList
    }
}