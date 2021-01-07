package igor.kuridza.dice.githubapp.ui.fragments.repositories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.*
import igor.kuridza.dice.githubapp.databinding.RepositoriesListFragmentBinding
import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.RepositoryListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoriesListFragment : BaseFragment<RepositoriesListFragmentBinding>(),
    RepositoryListAdapter.RepositoryClickListener,
    RepositoryListAdapter.OpenInBrowserClickListener,
    RepositoryListAdapter.AuthorDetailsClickListener
{
    private val viewModel: RepositoriesListViewModel by viewModel()
    private val repositoryAdapter by lazy {  RepositoryListAdapter(this, this, this) }
    private val args: RepositoriesListFragmentArgs by navArgs()

    override fun getLayoutResourceId(): Int = R.layout.repositories_list_fragment

    override fun setUpUi() {
        setupRecycler()
        searchRepositoriesByQuery(args.searchQuery)
        setSearchIconOnClickListener()
    }

    private fun setupRecycler(){
        binding.repositoryRecyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this@RepositoriesListFragment.context)
        }
    }

    private fun setSearchIconOnClickListener(){
        binding.searchButton.onClick {
            findNavController().navigate(R.id.goToSearchRepositoriesFragment)
        }
    }

    private fun searchRepositoriesByQuery(query: String){
        viewModel.getRepositoriesByQuery(query)
        viewModel.repositories.observe(this){
            when(it){
                is Resource.Success<RepositoriesResponse> ->  handleSuccess(it.data.repositories)
                is Resource.Error -> handleError()
                Resource.Loading -> handleLoading()
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
        val action = RepositoriesListFragmentDirections.goToRepositoryDetailsFragment(repository)
        findNavController().navigate(action)
    }

    override fun onOpenInBrowserClicked(repositoryUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(repositoryUrl)
        }
        startActivity(intent)
    }

    override fun onAuthorDetailsClicked(repositoryOwner: User) {
        val action = RepositoriesListFragmentDirections.goToUserDetailsFragment(repositoryOwner, OPEN_PROFILE)
        findNavController().navigate(action)
    }

    companion object{
        fun newsInstance(searchQuery: String): RepositoriesListFragment{
            return RepositoriesListFragment().apply {
                val bundle = Bundle()
                bundle.putString(SEARCH_QUERY_KEY, searchQuery)
                arguments = bundle
            }
        }
    }
}