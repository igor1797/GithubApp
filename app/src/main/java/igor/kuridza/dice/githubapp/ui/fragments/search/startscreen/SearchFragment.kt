package igor.kuridza.dice.githubapp.ui.fragments.search.startscreen

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.gone
import igor.kuridza.dice.githubapp.common.onClick
import igor.kuridza.dice.githubapp.common.visible
import igor.kuridza.dice.githubapp.databinding.FragmentSearchBinding
import igor.kuridza.dice.githubapp.model.CheckedSourcesState
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback
    private val searchFragmentViewModel: SearchFragmentViewModel by viewModel()
    private lateinit var badge: BadgeDrawable

    override fun getLayoutResourceId(): Int = R.layout.fragment_search

    override fun setUpUi() {
        initProperties()
        setClickListeners()
        addBottomSheetCallback()
        setCheckedSources()
        monitorsSearchInputChanges()
        observeCheckedSourcesState()
    }

    private fun initProperties(){
        initBadge()
        initDataBinding()
        initBehavior()
        initBottomSheetCallback()
    }

    private fun setClickListeners(){
        setFilterIconOnClickListener()
        setBackgroundBehindBottomSheetOnClickListener()
        setBottomSheetItemsOnClickListener()
        setCheckboxesOnClickListener()
        setSearchButtonOnClickListener()
    }

    private fun observeCheckedSourcesState(){
        searchFragmentViewModel.checkedSourcesState.observe(this){
            it?.let { checkedSourcesState ->
                badge.number = checkedSourcesState.getNumberOfCheckedSources()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun initBadge(){
        badge = BadgeDrawable.create(requireContext())
        binding.filterIcon.post {
            badge.verticalOffset = -20
            badge.badgeGravity = BadgeDrawable.TOP_START
            BadgeUtils.attachBadgeDrawable(badge, binding.filterIcon, binding.frameLayout)
            BadgeUtils.setBadgeDrawableBounds(badge, binding.filterIcon, binding.frameLayout)
        }
    }

    private fun initDataBinding(){
        binding.viewModel = searchFragmentViewModel
    }

    private fun initBehavior(){
        bottomSheetBehavior = BottomSheetBehavior.from(binding.chooseSourceBottomSheet)
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

    private fun setCheckedSources(){
        searchFragmentViewModel.checkedSourcesState.value?.let { checkedSourceState ->
            binding.apply {
                repositoriesCheckBox.isChecked = checkedSourceState.isRepositorySourceChecked
                usersCheckBox.isChecked = checkedSourceState.isUsersSourceChecked
            }
        }
    }

    private fun setSearchButtonOnClickListener() {
        binding.searchButton.onClick {
            val query = binding.searchInput.text.toString()
            searchFragmentViewModel.checkedSourcesState.value?.let { checkedSourcesState ->
                when {
                    checkedSourcesState.getNumberOfCheckedSources() == 2 -> showUsersAndRepositoriesList(query)
                    checkedSourcesState.isRepositorySourceChecked -> showRepositoriesList(query)
                    else -> showUsersList(query)
                }
                clearSearchInputQuery()
            }
        }
    }

    private fun showUsersAndRepositoriesList(searchQuery: String){
        val action = SearchFragmentDirections.goToUsersAndRepositoriesListFragment(searchQuery)
        findNavController().navigate(action)
    }

    private fun showRepositoriesList(searchQuery: String){
        val action = SearchFragmentDirections.goToRepositoriesListFragment(searchQuery)
        findNavController().navigate(action)
    }

    private fun showUsersList(searchQuery: String){
        val action = SearchFragmentDirections.goToUsersListFragment(searchQuery)
        findNavController().navigate(action)
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
            hideBottomSheet()
        }
    }

    private fun monitorsSearchInputChanges(){
        binding.searchInput.doOnTextChanged { searchQuery, _, _, _ ->
            searchFragmentViewModel.searchInputDataChanged(searchQuery.toString())
        }
    }

    private fun setCheckboxesOnClickListener() {
        binding.usersCheckBox.setOnCheckedChangeListener { _, checked ->
            val isCheckedAtLeastOneSource = binding.repositoriesCheckBox.isChecked || checked
            searchFragmentViewModel.changeStatusIsCheckedAtLeastOneSource(isCheckedAtLeastOneSource)
        }
        binding.repositoriesCheckBox.setOnCheckedChangeListener { _, checked ->
            val isCheckedAtLeastOneSource = binding.usersCheckBox.isChecked || checked
            searchFragmentViewModel.changeStatusIsCheckedAtLeastOneSource(isCheckedAtLeastOneSource)
        }
    }

    private fun addBottomSheetCallback(){
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
    }

    private fun removeBottomSheetCallback(){
        bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)
    }

    private fun setBackgroundBehindBottomSheetByNewBottomSheetState(newState: Int){
        when(newState){
            BottomSheetBehavior.STATE_HIDDEN -> hideBackgroundBehindBottomSheet()
            BottomSheetBehavior.STATE_EXPANDED -> showBackgroundBehindBottomSheet()
            else -> showBackgroundBehindBottomSheet()
        }
    }

    private fun showBackgroundBehindBottomSheet(){
        binding.backgroundBehindBottomSheet.visible()
    }

    private fun hideBackgroundBehindBottomSheet(){
        binding.backgroundBehindBottomSheet.gone()
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