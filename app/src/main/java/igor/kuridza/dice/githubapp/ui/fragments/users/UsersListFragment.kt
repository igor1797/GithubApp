package igor.kuridza.dice.githubapp.ui.fragments.users

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.SEARCH_QUERY_KEY
import igor.kuridza.dice.githubapp.databinding.UsersListFragmentBinding
import igor.kuridza.dice.githubapp.ui.adapters.UserListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class UsersListFragment : BaseFragment<UsersListFragmentBinding>() {

    private val viewModel: UsersListViewModel by viewModel()
    private val usersAdapter = UserListAdapter()
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
            it?.let {
                usersAdapter.setUsers(it)
            }
        }
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