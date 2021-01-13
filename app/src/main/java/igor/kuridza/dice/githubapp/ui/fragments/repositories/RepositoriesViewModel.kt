package igor.kuridza.dice.githubapp.ui.fragments.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RepositoriesListViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mRepositories = MutableLiveData<Resource<RepositoriesResponse>>()
    val repositories: LiveData<Resource<RepositoriesResponse>>
        get() = mRepositories

    fun getRepositoriesByQuery(query: String){
        viewModelScope.launch(IO) {
            searchRepository.getRepositoriesBySearchQuery(query).collect {
                mRepositories.postValue(it)
            }
        }
    }
}