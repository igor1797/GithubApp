package igor.kuridza.dice.githubapp.ui.fragments.search.searchusers

import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.*
import igor.kuridza.dice.githubapp.databinding.SearchUsersFragmentBinding
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.UserListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchUsersFragment : BaseFragment<SearchUsersFragmentBinding>(),
    UserListAdapter.OpenProfileClickListener,
    UserListAdapter.OpenRepositoriesListener{

    private val viewModel: UsersListViewModel by viewModel()
    private val usersAdapter by lazy { UserListAdapter(this, this) }

    override fun getLayoutResourceId(): Int = R.layout.search_users_fragment

    override fun setUpUi() {
        setupRecycler()
        setSearchIconOnClickListener()
        onBackPressed()
    }

    private fun onBackPressed(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setSearchIconOnClickListener(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchQuery ->
                    handleSearch(searchQuery)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun handleSearch(searchQuery: String){
        if (searchQuery.length >= 2)
            searchUsersByQuery(searchQuery)
        else
            showSnackbar(getString(R.string.snackbarQueryInfoMessage))
    }

    private fun searchUsersByQuery(query: String){
        viewModel.getUserByQuery(query)
        viewModel.userList.observe(this){
            when(it){
                is Resource.Success<*> -> handleSuccess(it.data as List<User>)
                is Resource.Loading -> handleLoading()
                is Resource.Error -> handleError()
            }
        }
    }

    private fun setupRecycler(){
        binding.usersRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    private fun handleSuccess(data: List<User>?){
        data?.let { users ->
            hideLoading()
            hideErrorMessage()
            if (users.isNotEmpty()) {
                usersAdapter.setUsers(users)
                showData()
                hideNoDataMessage()
            } else
                showNoDataMessage()
        }
    }

    private fun handleLoading(){
        hideData()
        hideErrorMessage()
        showLoading()
    }

    private fun handleError(){
        hideLoading()
        hideData()
        showErrorMessage()
    }

    private fun showNoDataMessage(){
        binding.noDataMessage.visible()
    }

    private fun hideNoDataMessage(){
        binding.noDataMessage.gone()
    }

    private fun showErrorMessage(){
        binding.errorMessage.visible()
    }

    private fun hideErrorMessage(){
        binding.errorMessage.gone()
    }

    private fun hideData(){
        binding.usersRecyclerView.gone()
    }

    private fun showData(){
        binding.usersRecyclerView.visible()
    }

    private fun hideLoading(){
        binding.progressBar.gone()
    }

    private fun showLoading(){
        binding.progressBar.visible()
    }

    override fun onOpenProfileClicked(user: User) {
        navigateToUserDetails(user, OPEN_PROFILE)
    }

    override fun onOpenRepositories(user: User) {
        navigateToUserDetails(user, OPEN_REPOSITORIES)
    }

    private fun navigateToUserDetails(user: User, action: String){
        val direction = SearchUsersFragmentDirections.goToUserDetailsFragment(user, action)
        findNavController().navigate(direction)
    }
}