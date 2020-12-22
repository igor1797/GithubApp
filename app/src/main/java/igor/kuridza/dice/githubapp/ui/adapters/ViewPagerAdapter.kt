package igor.kuridza.dice.githubapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListFragment
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabTitles: List<String>,
    val searchQuery: String
): FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RepositoriesListFragment.newsInstance(searchQuery)
            else -> UsersListFragment.newInstance(searchQuery)
        }
    }

    override fun getItemCount(): Int = tabTitles.size
}