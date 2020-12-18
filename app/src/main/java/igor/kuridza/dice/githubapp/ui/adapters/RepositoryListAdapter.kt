package igor.kuridza.dice.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
): RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>() {

    private val repositories = arrayListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RepositoryItemBinding>(inflater, R.layout.repository_item, parent, false)
        return RepositoryHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bindItem(repositories[position], repositoryItemClickListener, openInBrowserClickListener, authorDetailsClickListener)
    }

    override fun getItemCount(): Int = repositories.size

    fun setRepo(repos: List<Repository>){
        this.repositories.clear()
        this.repositories.addAll(repos)
        notifyDataSetChanged()
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
}