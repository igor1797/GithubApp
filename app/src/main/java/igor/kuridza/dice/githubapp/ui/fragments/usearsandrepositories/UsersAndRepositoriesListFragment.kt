package igor.kuridza.dice.githubapp.ui.fragments.usearsandrepositories

import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.REPOSITORIES_TAB_NAME
import igor.kuridza.dice.githubapp.common.USERS_TAB_NAME
import igor.kuridza.dice.githubapp.databinding.UsersAndRepositoriesListFragmentBinding
import igor.kuridza.dice.githubapp.ui.adapters.ViewPagerAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment

class UsersAndRepositoriesListFragment : BaseFragment<UsersAndRepositoriesListFragmentBinding>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private val args: UsersAndRepositoriesListFragmentArgs by navArgs()

    override fun getLayoutResourceId(): Int = R.layout.users_and_repositories_list_fragment

    override fun setUpUi() {
        initViewPagerAdapter()
        setupTabLayoutAndViewPager()
    }

    private fun initViewPagerAdapter(){
        activity?.let { fragmentActivity ->
            viewPagerAdapter = ViewPagerAdapter(
                fragmentActivity = fragmentActivity,
                tabTitles = listOf(REPOSITORIES_TAB_NAME, USERS_TAB_NAME),
                searchQuery = args.searchQuery
            )
        }
    }

    private fun initAndAttachTabLayoutMediator(){
        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = viewPagerAdapter.tabTitles[position]
        }
        tabLayoutMediator.attach()
    }

    private fun setupTabLayoutAndViewPager(){
        binding.viewPager.adapter = viewPagerAdapter
        initAndAttachTabLayoutMediator()
    }

    private fun detachTabLayoutMediator(){
        tabLayoutMediator.detach()
    }

    override fun onDestroyView() {
        detachTabLayoutMediator()
        super.onDestroyView()
    }
}