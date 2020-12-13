package igor.kuridza.dice.githubapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.databinding.RepositoryItemBinding
import igor.kuridza.dice.githubapp.model.Repository

class RepositoryListAdapter: RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>() {

    private val repositories = arrayListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RepositoryItemBinding>(inflater, R.layout.repository_item, parent, false)
        return RepositoryHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bindItem(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    fun setRepo(repos: List<Repository>){
        this.repositories.clear()
        this.repositories.addAll(repos)
        notifyDataSetChanged()
    }

    inner class RepositoryHolder(private val binding: RepositoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(repository: Repository){
            binding.repository = repository
        }
    }
}