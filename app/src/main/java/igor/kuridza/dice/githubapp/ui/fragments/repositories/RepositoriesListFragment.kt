package igor.kuridza.dice.githubapp.ui.fragments.repositories

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.SEARCH_QUERY_KEY
import igor.kuridza.dice.githubapp.databinding.RepositoriesListFragmentBinding
import igor.kuridza.dice.githubapp.ui.adapters.RepositoryListAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoriesListFragment : BaseFragment<RepositoriesListFragmentBinding>() {

    private val viewModel: RepositoriesListViewModel by viewModel()
    private val repositoryAdapter = RepositoryListAdapter()
    private val args: RepositoriesListFragmentArgs by navArgs()

    override fun getLayoutResourceId(): Int = R.layout.repositories_list_fragment

    override fun setUpUi() {
        setupRecycler()
        searchRepositoriesByQuery(args.searchQuery)
        observeRepositories()
    }

    private fun setupRecycler(){
        binding.repositoryRecyclerView.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this@RepositoriesListFragment.context)
        }
    }

    private fun searchRepositoriesByQuery(query: String){
        viewModel.getRepositoriesVyQuery(query)
    }

    private fun observeRepositories(){
        viewModel.repositoriesList.observe(this){
            it?.let {
                repositoryAdapter.setRepo(it)
            }
        }
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