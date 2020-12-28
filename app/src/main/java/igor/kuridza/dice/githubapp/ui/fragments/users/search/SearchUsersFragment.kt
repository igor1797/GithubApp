package igor.kuridza.dice.githubapp.ui.fragments.users.search

import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.showSnackbar
import igor.kuridza.dice.githubapp.databinding.SearchUsersFragmentBinding
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListContainerFragment

class SearchUsersFragment : UsersListContainerFragment<SearchUsersFragmentBinding>(){

    override val layoutId: Int
        get() =  R.layout.search_users_fragment

    override val noData: TextView
        get() = binding.noDataMessage

    override val errorMessage: TextView
        get() = binding.errorMessage

    override val progressBar: ProgressBar
        get() = binding.progressBar

    override val recyclerView: RecyclerView
        get() = binding.usersRecyclerView

    override fun setUpUi() {
        onBackPressed()
        setupRecycler()
        setSearchIconOnClickListener()
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
}