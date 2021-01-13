package igor.kuridza.dice.githubapp.ui.fragments.repositories.list

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.*
import igor.kuridza.dice.githubapp.databinding.RepositoriesListFragmentBinding
import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListContainerFragment

class RepositoriesListFragment : RepositoriesListContainerFragment<RepositoriesListFragmentBinding>(){

    private val args: RepositoriesListFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.repositories_list_fragment

    override val errorMessage: TextView
        get() = binding.errorMessage

    override val noData: TextView
        get() = binding.noDataMessage

    override val progressBar: ProgressBar
        get() = binding.progressBar

    override val recyclerView: RecyclerView
        get() = binding.repositoryRecyclerView

    override fun setUpUi() {
        setupRecycler()
        setSearchIconOnClickListener()
        searchRepositoriesByQuery(args.searchQuery)
    }

    private fun setSearchIconOnClickListener(){
        binding.searchButton.onClick {
            findNavController().navigate(R.id.goToSearchRepositoriesFragment)
        }
    }

    companion object{
        fun newsInstance(searchQuery: String): RepositoriesListFragment {
            return RepositoriesListFragment().apply {
                val bundle = Bundle()
                bundle.putString(SEARCH_QUERY_KEY, searchQuery)
                arguments = bundle
            }
        }
    }
}