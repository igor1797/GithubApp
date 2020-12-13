package igor.kuridza.dice.githubapp.ui.fragments.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import igor.kuridza.dice.githubapp.model.CheckedSourcesState
import igor.kuridza.dice.githubapp.model.SelectedSource

class SearchFragmentViewModel: ViewModel(){

    private val mSearchInputIsDataValid = MutableLiveData(false)
    val searchInputIsDataValid: LiveData<Boolean>
        get() = mSearchInputIsDataValid

    private val mSelectedSource = MutableLiveData(SelectedSource.REPOSITORY_SOURCE)
    val selectedSource: LiveData<SelectedSource>
        get() = mSelectedSource

    private val mIsCheckedAtLeastOneSource = MutableLiveData(true)
    val isCheckedAtLeastOneSource: LiveData<Boolean>
        get() = mIsCheckedAtLeastOneSource

    private val mCheckedSourcesState = MutableLiveData(CheckedSourcesState())
    val checkedSourcesState: LiveData<CheckedSourcesState>
        get() = mCheckedSourcesState

    fun changeSelectedSource(newSource: SelectedSource){
        mSelectedSource.value = newSource
    }

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