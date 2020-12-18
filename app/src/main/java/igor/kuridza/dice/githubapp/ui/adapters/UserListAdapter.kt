package igor.kuridza.dice.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.onClick
import igor.kuridza.dice.githubapp.databinding.UserItemBinding
import igor.kuridza.dice.githubapp.model.User

class UserListAdapter(
    private val openProfileClickListener: OpenProfileClickListener,
    private val openRepositoriesListener: OpenRepositoriesListener
): RecyclerView.Adapter<UserListAdapter.UserListHolder>(){

    private val userList = arrayListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<UserItemBinding>(inflater, R.layout.user_item, parent, false)
        return UserListHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        holder.bindItem(userList[position], openProfileClickListener, openRepositoriesListener)
    }

    override fun getItemCount(): Int = userList.size

    fun setUsers(users: List<User>){
        this.userList.clear()
        this.userList.addAll(users)
        notifyDataSetChanged()
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
}