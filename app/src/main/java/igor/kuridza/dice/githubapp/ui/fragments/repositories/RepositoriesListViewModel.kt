package igor.kuridza.dice.githubapp.ui.fragments.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RepositoriesListViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private var mRepositoriesList = MutableLiveData<Resource<List<Repository>>>()
    val repositoriesList: LiveData<Resource<List<Repository>>>
        get() = mRepositoriesList

    fun getRepositoriesByQuery(query: String) {
        viewModelScope.launch(IO) {
            mRepositoriesList.postValue(Resource.Loading())
            try {
                val response = searchRepository.getRepositoriesBySearchQuery(query)
                if(response.isSuccessful) {
                    response.body()?.let { repositoriesResponse ->
                        mRepositoriesList.postValue(Resource.Success(repositoriesResponse.repositories))
                    }
                }else{
                    mRepositoriesList.postValue(Resource.Error(response.message()))
                }
            }catch (e: Exception){
                mRepositoriesList.postValue(Resource.Error(e.message))
            }
        }
    }
}