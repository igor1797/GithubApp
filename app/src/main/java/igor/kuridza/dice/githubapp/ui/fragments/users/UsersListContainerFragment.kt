package igor.kuridza.dice.githubapp.ui.fragments.users

import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.common.OPEN_PROFILE
import igor.kuridza.dice.githubapp.common.OPEN_REPOSITORIES
import igor.kuridza.dice.githubapp.common.gone
import igor.kuridza.dice.githubapp.common.visible
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.UserListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.githubapp.ui.fragments.users.list.UsersListFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

abstract class UsersListContainerFragment<viewDataBinding: ViewDataBinding>: BaseFragment<viewDataBinding>(),
    UserListAdapter.OpenProfileClickListener,
    UserListAdapter.OpenRepositoriesListener
{
    protected abstract val layoutId: Int
    protected abstract val recyclerView: RecyclerView
    protected abstract val noData: TextView
    protected abstract val errorMessage: TextView
    protected abstract val progressBar: ProgressBar

    private val viewModel: UsersViewModel by viewModel()
    private val usersAdapter by lazy { UserListAdapter(this, this) }

    override fun getLayoutResourceId() = layoutId

    protected fun searchUsersByQuery(query: String) {
        hideErrorMessage()
        hideNoDataMessage()
        viewModel.getUsersByQuery(query)
        viewModel.users.observe(this) {
            when (it) {
                is Resource.Success -> handleSuccess(it.data.users)
                is Resource.Loading -> handleLoading()
                is Resource.Error -> handleError()
            }
        }
    }

    protected fun setupRecycler() {
        recyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handleSuccess(data: List<User>?) {
        data?.let { users ->
            hideLoading()
            hideErrorMessage()
            if (users.isNotEmpty()) {
                usersAdapter.submitList(users)
                showData()
                hideNoDataMessage()
            } else
                showNoDataMessage()
        }
    }

    private fun handleLoading() {
        hideData()
        hideErrorMessage()
        showLoading()
    }

    private fun handleError() {
        hideLoading()
        hideData()
        showErrorMessage()
    }

    private fun showNoDataMessage() {
        noData.visible()
    }

    private fun hideNoDataMessage() {
        noData.gone()
    }

    private fun showErrorMessage() {
        errorMessage.visible()
    }

    private fun hideErrorMessage() {
        errorMessage.gone()
    }

    private fun hideData() {
        recyclerView.gone()
    }

    private fun showData() {
        recyclerView.visible()
    }

    private fun hideLoading() {
        progressBar.gone()
    }

    private fun showLoading() {
        progressBar.visible()
    }

    override fun onOpenProfileClicked(user: User) {
        navigateToUserDetails(user, OPEN_PROFILE)
    }

    override fun onOpenRepositories(user: User) {
        navigateToUserDetails(user, OPEN_REPOSITORIES)
    }

    private fun navigateToUserDetails(user: User, action: String) {
        val direction = UsersListFragmentDirections.goToUserDetailsFragment(user, action)
        findNavController().navigate(direction)
    }
}