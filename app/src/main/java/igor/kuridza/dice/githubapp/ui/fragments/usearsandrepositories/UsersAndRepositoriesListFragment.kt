package igor.kuridza.dice.githubapp.ui.fragments.usearsandrepositories

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.REPOSITORIES_TAB_NAME
import igor.kuridza.dice.githubapp.common.USERS_TAB_NAME
import igor.kuridza.dice.githubapp.databinding.UsersAndRepositoriesListFragmentBinding
import igor.kuridza.dice.githubapp.ui.adapters.ViewPagerAdapter
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment
import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListFragment
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListFragment

class UsersAndRepositoriesListFragment : BaseFragment<UsersAndRepositoriesListFragmentBinding>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
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
                fragments = listOf(RepositoriesListFragment.newsInstance(args.searchQuery), UsersListFragment.newInstance(args.searchQuery)),
                tabTitles = listOf(REPOSITORIES_TAB_NAME, USERS_TAB_NAME)
            )
        }
    }

    private fun setupTabLayoutAndViewPager(){
        binding.viewPager.adapter = viewPagerAdapter
        attachTabLayoutAndViewPager()
    }

    private fun attachTabLayoutAndViewPager(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            binding.viewPager.setCurrentItem(tab.position, true)
            tab.text = viewPagerAdapter.tabTitles[position]
        }.attach()
    }

    private fun detachTabLayoutAndViewPager(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                _, _ ->
        }.detach()
    }

    override fun onDestroyView() {
        detachTabLayoutAndViewPager()
        super.onDestroyView()
    }
}