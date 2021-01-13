package igor.kuridza.dice.githubapp.ui.fragments.repositories

import android.content.Intent
import android.net.Uri
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.common.OPEN_PROFILE
import igor.kuridza.dice.githubapp.common.gone
import igor.kuridza.dice.githubapp.common.visible
import igor.kuridza.dice.githubapp.model.RepositoriesResponse
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.Resource
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.adapters.RepositoryListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.githubapp.ui.fragments.repositories.search.SearchRepositoriesFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

abstract class RepositoriesListContainerFragment<viewDataBinding: ViewDataBinding>:
    BaseFragment<viewDataBinding>(),
    RepositoryListAdapter.RepositoryClickListener,
    RepositoryListAdapter.OpenInBrowserClickListener,
    RepositoryListAdapter.AuthorDetailsClickListener{

    protected abstract val layoutId: Int
    protected abstract val noData: TextView
    protected abstract val errorMessage: TextView
    protected abstract val progressBar: ProgressBar
    protected abstract val recyclerView: RecyclerView
    private val viewModel: RepositoriesListViewModel by viewModel()
    private val repositoryAdapter by lazy {  RepositoryListAdapter(this, this, this) }

    override fun getLayoutResourceId(): Int = layoutId

    protected fun setupRecycler(){
        recyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }

    protected fun searchRepositoriesByQuery(query: String){
        hideErrorMessage()
        hideNoDataMessage()
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
        noData.visible()
    }

    private fun hideNoDataMessage(){
        noData.gone()
    }

    private fun showErrorMessage(){
        errorMessage.visible()
    }

    private fun hideErrorMessage(){
        errorMessage.gone()
    }

    private fun hideData() {
        recyclerView.gone()
    }

    private fun showData(){
        recyclerView.visible()
    }

    private fun hideLoading(){
        progressBar.gone()
    }

    private fun showLoading(){
        progressBar.visible()
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