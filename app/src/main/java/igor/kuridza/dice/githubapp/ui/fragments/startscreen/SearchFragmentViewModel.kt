package igor.kuridza.dice.githubapp.ui.fragments.startscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.githubapp.model.CheckedSourcesState

class SearchFragmentViewModel: ViewModel(){

    private val mSearchInputIsDataValid = MutableLiveData(false)
    val searchInputIsDataValid: LiveData<Boolean>
        get() = mSearchInputIsDataValid

    private val mIsCheckedAtLeastOneSource = MutableLiveData(true)
    val isCheckedAtLeastOneSource: LiveData<Boolean>
        get() = mIsCheckedAtLeastOneSource

    private val mCheckedSourcesState = MutableLiveData(CheckedSourcesState())
    val checkedSourcesState: LiveData<CheckedSourcesState>
        get() = mCheckedSourcesState

    fun searchInputDataChanged(searchQuery: String){
        mSearchInputIsDataValid.value = searchQuery.length >= 2
    }

    fun changeStatusIsCheckedAtLeastOneSource(isCheckedAtLeastOneSource: Boolean){
       mIsCheckedAtLeastOneSource.value = isCheckedAtLeastOneSource
    }

    fun setSelectedSources(checkedSourcesState: CheckedSourcesState){
        mCheckedSourcesState.value = checkedSourcesState
    }
}