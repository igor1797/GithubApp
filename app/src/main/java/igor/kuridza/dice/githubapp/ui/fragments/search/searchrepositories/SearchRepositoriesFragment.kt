package igor.kuridza.dice.githubapp.ui.fragments.search.searchrepositories

import android.content.Intent
import android.net.Uri
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.OPEN_PROFILE
import igor.kuridza.dice.githubapp.common.gone
import igor.kuridza.dice.githubapp.common.showSnackbar
import igor.kuridza.dice.githubapp.common.visible
import igor.kuridza.dice.githubapp.databinding.SearchRepositoriesFragmentBinding
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.RepositoryListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchRepositoriesFragment : BaseFragment<SearchRepositoriesFragmentBinding>(),
    RepositoryListAdapter.AuthorDetailsClickListener,
    RepositoryListAdapter.OpenInBrowserClickListener,
    RepositoryListAdapter.RepositoryClickListener{

    private val viewModel: RepositoriesListViewModel by viewModel()
    private val repositoryAdapter by lazy {  RepositoryListAdapter(this, this, this) }

    override fun getLayoutResourceId(): Int = R.layout.search_repositories_fragment

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

    private fun setupRecycler(){
        binding.repositoryRecyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this.context)
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

    private fun searchRepositoriesByQuery(query: String){
        hideErrorMessage()
        hideNoDataMessage()
        viewModel.getRepositoriesVyQuery(query)
        viewModel.repositoriesList.observe(this){
            when(it){
                is Resource.Success<*> -> handleSuccess(it.data as List<Repository>)
                is Resource.Error -> handleError()
                is Resource.Loading -> handleLoading()
            }
        }
    }

    private fun handleSuccess(data: List<Repository>?){
        data?.let { repositories ->
            hideLoading()
            hideErrorMessage()
            if (repositories.isNotEmpty()) {
                repositoryAdapter.submitList(repositories)
                showData()
                hideNoDataMessage()
            } else
                showNoDataMessage()
        }
    }

    private fun handleError(){
        hideLoading()
        hideData()
        showErrorMessage()
    }

    private fun handleLoading(){
        hideData()
        hideErrorMessage()
        showLoading()
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
        binding.repositoryRecyclerView.gone()
    }

    private fun showData(){
        binding.repositoryRecyclerView.visible()
    }

    private fun hideLoading(){
        binding.progressBar.gone()
    }

    private fun showLoading(){
        binding.progressBar.visible()
    }

    override fun onRepositoryClicked(repository: Repository) {
        val action = SearchRepositoriesFragmentDirections.goToRepositoryDetailsFragment(repository)
        findNavController().navigate(action)
    }

    override fun onOpenInBrowserClicked(repositoryUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(repositoryUrl)
        }
        startActivity(intent)
    }

    override fun onAuthorDetailsClicked(repositoryOwner: User) {
        val action = SearchRepositoriesFragmentDirections.goToUserDetailsFragment(repositoryOwner, OPEN_PROFILE)
        findNavController().navigate(action)
    }
}