package igor.kuridza.dice.githubapp.ui.fragments.users.list

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.*
import igor.kuridza.dice.githubapp.databinding.UsersListFragmentBinding
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListContainerFragment

class UsersListFragment : UsersListContainerFragment<UsersListFragmentBinding>(){

    private val args: UsersListFragmentArgs by navArgs()

    override val layoutId: Int
        get() =  R.layout.users_list_fragment

    override val noData: TextView
        get() = binding.noDataMessage

    override val errorMessage: TextView
        get() = binding.errorMessage

    override val progressBar: ProgressBar
        get() = binding.progressBar

    override val recyclerView: RecyclerView
        get() = binding.usersRecyclerView

    override fun setUpUi() {
        setupRecycler()
        searchUsersByQuery(args.searchQuery)
        setSearchButtonOnClickListener()
    }

    private fun setSearchButtonOnClickListener(){
        binding.searchButton.onClick {
            findNavController().navigate(R.id.goToSearchUsersFragment)
        }
    }

    companion object{
        fun newInstance(searchQuery: String): UsersListFragment {
            return UsersListFragment().apply {
                val bundle = Bundle()
                bundle.putString(SEARCH_QUERY_KEY, searchQuery)
                arguments = bundle
            }
        }
    }
}