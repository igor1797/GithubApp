package igor.kuridza.dice.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.onClick
import igor.kuridza.dice.githubapp.databinding.UserItemBinding
import igor.kuridza.dice.githubapp.model.User

class UserListAdapter(
    private val openProfileClickListener: OpenProfileClickListener,
    private val openRepositoriesListener: OpenRepositoriesListener
): ListAdapter<User, UserListAdapter.UserListHolder>(COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<UserItemBinding>(inflater, R.layout.user_item, parent, false)
        return UserListHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        val mUser = getItem(position)
        mUser?.let { user ->
            holder.bindItem(user, openProfileClickListener, openRepositoriesListener)
        }
    }

    interface OpenProfileClickListener{
        fun onOpenProfileClicked(user: User)
    }

    interface OpenRepositoriesListener{
        fun onOpenRepositories(user: User)
    }

    inner class UserListHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(mUser: User, openProfileClickListener: OpenProfileClickListener, openRepositoriesListener: OpenRepositoriesListener){
            binding.apply {
                user = mUser
                openProfile.onClick {
                    openProfileClickListener.onOpenProfileClicked(mUser)
                }
                openRepositories.onClick {
                    openRepositoriesListener.onOpenRepositories(mUser)
                }
            }
        }
    }

    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}