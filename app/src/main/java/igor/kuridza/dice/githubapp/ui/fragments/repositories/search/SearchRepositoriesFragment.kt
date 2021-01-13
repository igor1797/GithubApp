package igor.kuridza.dice.githubapp.ui.fragments.repositories.search

import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.showSnackbar
import igor.kuridza.dice.githubapp.databinding.SearchRepositoriesFragmentBinding
import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListContainerFragment

class SearchRepositoriesFragment : RepositoriesListContainerFragment<SearchRepositoriesFragmentBinding>(){

    override val layoutId: Int
        get() = R.layout.search_repositories_fragment

    override val noData: TextView
        get() = binding.noDataMessage

    override val errorMessage: TextView
        get() = binding.errorMessage

    override val progressBar: ProgressBar
        get() = binding.progressBar

    override val recyclerView: RecyclerView
        get() = binding.repositoryRecyclerView

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
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
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
            searchRepositoriesByQuery(searchQuery)
        else
            showSnackbar(getString(R.string.snackbarQueryInfoMessage))
    }
}