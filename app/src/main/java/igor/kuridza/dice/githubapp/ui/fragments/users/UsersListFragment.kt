package igor.kuridza.dice.githubapp.ui.fragments.users

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.*
import igor.kuridza.dice.githubapp.databinding.UsersListFragmentBinding
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.UserListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class UsersListFragment : BaseFragment<UsersListFragmentBinding>(),
    UserListAdapter.OpenProfileClickListener,
    UserListAdapter.OpenRepositoriesListener {

    private val viewModel: UsersListViewModel by viewModel()
    private val usersAdapter by lazy { UserListAdapter(this, this) }
    private val args: UsersListFragmentArgs by navArgs()

    override fun getLayoutResourceId(): Int = R.layout.users_list_fragment

    override fun setUpUi() {
        setupRecycler()
        searchUsersByQuery(args.searchQuery)
        observeUsers()
    }

    private fun searchUsersByQuery(query: String){
        viewModel.getUserByQuery(query)
    }

    private fun setupRecycler(){
        binding.usersRecyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@UsersListFragment.context)
        }
    }

    private fun observeUsers(){
        viewModel.userList.observe(this){
            when(it){
                is Resource.Success -> handleSuccess(it.data)
                is Resource.Loading -> handleLoading()
                is Resource.Error -> handleError()
            }
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
        val direction = UsersListFragmentDirections.goToUserDetailsFragment(user, action)
        findNavController().navigate(direction)
    }

    companion object{
        fun newInstance(searchQuery: String): UsersListFragment{
            return UsersListFragment().apply {
                val bundle = Bundle()
                bundle.putString(SEARCH_QUERY_KEY, searchQuery)
                arguments = bundle
            }
        }
    }
}