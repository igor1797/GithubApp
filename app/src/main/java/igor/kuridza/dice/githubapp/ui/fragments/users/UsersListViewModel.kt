package igor.kuridza.dice.githubapp.ui.fragments.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.repositories.SearchRepository

class UsersListViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mUserList = MutableLiveData<Resource<List<User>>>()
    val userList: LiveData<Resource<List<User>>>
        get() = mUserList

    fun getUserByQuery(query: String){
        mUserList = searchRepository.getUsersBySearchQuery(query) as MutableLiveData<Resource<List<User>>>
    }
}