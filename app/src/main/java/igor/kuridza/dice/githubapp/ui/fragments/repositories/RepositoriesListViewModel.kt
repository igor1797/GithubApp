package igor.kuridza.dice.githubapp.ui.fragments.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.repositories.SearchRepository

class RepositoriesListViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mRepositoriesList = MutableLiveData<List<Repository>>()
    val repositoriesList: LiveData<List<Repository>>
        get() = mRepositoriesList

    fun getRepositoriesVyQuery(query: String){
        mRepositoriesList = searchRepository.getRepositoriesBySearchQuery(query) as MutableLiveData<List<Repository>>
    }
}