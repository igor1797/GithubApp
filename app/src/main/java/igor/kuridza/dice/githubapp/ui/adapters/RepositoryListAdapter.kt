package igor.kuridza.dice.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.onClick
import igor.kuridza.dice.githubapp.databinding.RepositoryItemBinding
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.model.User

class RepositoryListAdapter(
    private val repositoryItemClickListener: RepositoryClickListener,
    private val openInBrowserClickListener: OpenInBrowserClickListener,
    private val authorDetailsClickListener: AuthorDetailsClickListener
): ListAdapter<Repository, RepositoryListAdapter.RepositoryHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RepositoryItemBinding>(inflater, R.layout.repository_item, parent, false)
        return RepositoryHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val mRepository = getItem(position)
        mRepository?.let { repository ->
            holder.bindItem(repository, repositoryItemClickListener, openInBrowserClickListener, authorDetailsClickListener)
        }
    }

    interface RepositoryClickListener{
        fun onRepositoryClicked(repository: Repository)
    }

    interface OpenInBrowserClickListener{
        fun onOpenInBrowserClicked(repositoryUrl: String)
    }

    interface AuthorDetailsClickListener{
        fun onAuthorDetailsClicked(repositoryOwner: User)
    }

    inner class RepositoryHolder(private val binding: RepositoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(
            mRepository: Repository,
            repositoryItemClickListener: RepositoryClickListener,
            openInBrowserClickListener: OpenInBrowserClickListener,
            authorDetailsClickListener: AuthorDetailsClickListener
        ){
            binding.apply {
                repository = mRepository
                repositoryCard.onClick {
                    repositoryItemClickListener.onRepositoryClicked(mRepository)
                }
                openInBrowser.onClick {
                    openInBrowserClickListener.onOpenInBrowserClicked(mRepository.repositoryUrl)
                }
                authorDetails.onClick {
                    authorDetailsClickListener.onAuthorDetailsClicked(mRepository.owner)
                }
            }
        }
    }

    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }
}