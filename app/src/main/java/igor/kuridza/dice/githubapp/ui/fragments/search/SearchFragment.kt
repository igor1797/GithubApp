package igor.kuridza.dice.githubapp.ui.fragments.search

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.checkbox.MaterialCheckBox
import igor.kuridza.dice.githubapp.*
import igor.kuridza.dice.githubapp.common.gone
import igor.kuridza.dice.githubapp.common.onClick
import igor.kuridza.dice.githubapp.common.visible
import igor.kuridza.dice.githubapp.databinding.FragmentSearchBinding
import igor.kuridza.dice.githubapp.model.CheckedSourcesState
import igor.kuridza.dice.githubapp.model.SelectedSource
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback
    private lateinit var afterTextChangedListener: TextWatcher
    private val searchFragmentViewModel: SearchFragmentViewModel by viewModel()
    private lateinit var badge: BadgeDrawable
    private val checkedSourcesList = arrayListOf<MaterialCheckBox>()

    override fun getLayoutResourceId(): Int = R.layout.fragment_search

    override fun setUpUi() {
        initDataBinding()
        initBehavior()
        initBottomSheetCallback()
        initBadge()
        setFilterIconOnClickListener()
        setBackgroundBehindBottomSheetOnClickListener()
        setBottomSheetItemsOnClickListener()
        setCheckboxesOnClickListener()
        setSearchButtonOnClickListener()
        addBottomSheetCallback()
        setCheckedSources()
        monitorsSearchInputChanges()
    }

    @SuppressLint("RestrictedApi")
    private fun initBadge(){
        badge = BadgeDrawable.create(requireContext())
        binding.filterIcon.post {
            badge.verticalOffset = -20
            badge.number = 1
            badge.badgeGravity = BadgeDrawable.TOP_START
            BadgeUtils.attachBadgeDrawable(badge, binding.filterIcon, binding.frameLayout)
            BadgeUtils.setBadgeDrawableBounds(badge, binding.filterIcon, binding.frameLayout)
        }
    }

    private fun initDataBinding(){
        binding.viewModel = searchFragmentViewModel
    }

    private fun setCheckedSources(){
        searchFragmentViewModel.checkedSourcesState.value?.let { checkedSourceState ->
            binding.apply {
                repositoriesCheckBox.isChecked = checkedSourceState.isRepositorySourceChecked
                usersCheckBox.isChecked = checkedSourceState.isUsersSourceChecked
            }
        }
    }

    private fun setSearchButtonOnClickListener(){
        binding.searchButton.onClick {
            val query = binding.searchInput.text.toString()
            when(searchFragmentViewModel.selectedSource.value){
                SelectedSource.BOTH_SOURCE -> {
                    val action = SearchFragmentDirections.goToUsersAndRepositoriesListFragment(query)
                    findNavController().navigate(action)
                }
                SelectedSource.REPOSITORY_SOURCE -> {
                    val action = SearchFragmentDirections.goToRepositoriesListFragment(query)
                    findNavController().navigate(action)
                }
                SelectedSource.USERS_SOURCE -> {
                    val action = SearchFragmentDirections.goToUsersListFragment(query)
                    findNavController().navigate(action)
                }
            }
            clearSearchInputQuery()
        }
    }

    private fun clearSearchInputQuery(){
        binding.searchInput.setText("")
    }

    private fun setFilterIconOnClickListener(){
        binding.filterIcon.onClick {
            expandBottomSheet()
        }
    }


    private fun setBackgroundBehindBottomSheetOnClickListener(){
        binding.backgroundBehindBottomSheet.onClick {
            hideBottomSheet()
            setCheckedSources()
        }
    }

    private fun setBottomSheetItemsOnClickListener() {
        binding.bottomSheetHeader.onClick {
            hideBottomSheet()
            setCheckedSources()
        }

        binding.applyButton.onClick {
            val repoSelected = binding.repositoriesCheckBox.isChecked
            val userSelected = binding.usersCheckBox.isChecked
            searchFragmentViewModel.setSelectedSources(CheckedSourcesState(repoSelected, userSelected))
            if (repoSelected && userSelected)
                searchFragmentViewModel.changeSelectedSource(SelectedSource.BOTH_SOURCE)
            else if (repoSelected)
                searchFragmentViewModel.changeSelectedSource(SelectedSource.REPOSITORY_SOURCE)
            else
                searchFragmentViewModel.changeSelectedSource(SelectedSource.USERS_SOURCE)
            setBadgeNumberOfCheckedSources()
            hideBottomSheet()
        }
    }

    private fun setBadgeNumberOfCheckedSources(){
        badge.number = checkedSourcesList.size
    }

    private fun initBehavior(){
        bottomSheetBehavior = BottomSheetBehavior.from(binding.chooseSourceBottomSheet)
    }

    private fun monitorsSearchInputChanges(){
        binding.searchInput.doOnTextChanged { searchQuery, _, _, _ ->
            searchFragmentViewModel.searchInputDataChanged(searchQuery.toString())
        }
    }

    private fun setCheckboxesOnClickListener() {
        binding.usersCheckBox.setOnCheckedChangeListener { checkBox, checked ->
            val isCheckedAtLeastOneSource = binding.repositoriesCheckBox.isChecked || checked
            searchFragmentViewModel.changeStatusIsCheckedAtLeastOneSource(isCheckedAtLeastOneSource)
            addOrRemoveCheckBox(checked, checkBox as MaterialCheckBox)
        }
        binding.repositoriesCheckBox.setOnCheckedChangeListener { checkBox, checked ->
            val isCheckedAtLeastOneSource = binding.usersCheckBox.isChecked || checked
            searchFragmentViewModel.changeStatusIsCheckedAtLeastOneSource(isCheckedAtLeastOneSource)
            addOrRemoveCheckBox(checked, checkBox as MaterialCheckBox)
        }
    }

    private fun addOrRemoveCheckBox(checked: Boolean, checkBox: MaterialCheckBox){
        if(checked)
            checkedSourcesList.add(checkBox)
        else
            checkedSourcesList.remove(checkBox)
    }

    private fun initBottomSheetCallback(){
        bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                setBackgroundBehindBottomSheetByNewBottomSheetState(newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                changeAlphaOfBackgroundBehindBottomSheet(slideOffset)
            }
        }
    }

    private fun addBottomSheetCallback(){
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private fun removeBottomSheetCallback(){
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)
    }

    private fun setBackgroundBehindBottomSheetByNewBottomSheetState(newState: Int){
        val background = binding.backgroundBehindBottomSheet
        when(newState){
            BottomSheetBehavior.STATE_HIDDEN -> background.gone()
            BottomSheetBehavior.STATE_EXPANDED -> background.visible()
            else -> background.visible()
        }
    }

    private fun changeAlphaOfBackgroundBehindBottomSheet(slideOffset: Float){
        binding.backgroundBehindBottomSheet.alpha = slideOffset - 0.2F
    }

    private fun expandBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet(){
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    @SuppressLint("RestrictedApi")
    private fun detachBadgeDrawable(){
        BadgeUtils.detachBadgeDrawable(badge, binding.filterIcon, binding.frameLayout)
    }

    override fun onDestroyView() {
        detachBadgeDrawable()
        removeBottomSheetCallback()
        super.onDestroyView()
    }
}