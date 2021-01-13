package igor.kuridza.dice.githubapp.ui.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.UsersResponse
import igor.kuridza.dice.githubapp.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UsersViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mUsers = MutableLiveData<Resource<UsersResponse>>()
    val users: LiveData<Resource<UsersResponse>>
        get() = mUsers

    fun getUsersByQuery(query: String){
        viewModelScope.launch(IO) {
            searchRepository.getUsersBySearchQuery(query).collect {
                mUsers.postValue(it)
            }
        }
    }
}