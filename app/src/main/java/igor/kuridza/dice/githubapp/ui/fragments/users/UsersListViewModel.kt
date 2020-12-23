package igor.kuridza.dice.githubapp.ui.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mUserList = MutableLiveData<Resource<List<User>>>()
    val userList: LiveData<Resource<List<User>>>
        get() = mUserList

    fun getUserByQuery(query: String) {
        viewModelScope.launch(IO) {
            mUserList.postValue(Resource.Loading())
            try {
                val response = searchRepository.getUsersBySearchQuery(query)
                if(response.isSuccessful){
                    response.body()?.let { usersResponse ->
                        mUserList.postValue(Resource.Success(usersResponse.users))
                    }
                }else
                    mUserList.postValue(Resource.Error(response.message()))
            }catch (e: Exception){
                mUserList.postValue(Resource.Error(e.message))
            }
        }
    }
}