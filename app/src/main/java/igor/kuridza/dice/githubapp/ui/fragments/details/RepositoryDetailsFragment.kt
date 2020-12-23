package igor.kuridza.dice.githubapp.ui.fragments.details

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.databinding.FragmentRepositoryDetailsBinding
import igor.kuridza.dice.githubapp.model.Repository
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment

class RepositoryDetailsFragment : BaseFragment<FragmentRepositoryDetailsBinding>() {

    private val args: RepositoryDetailsFragmentArgs by navArgs()
    private lateinit var repository: Repository

    override fun getLayoutResourceId(): Int = R.layout.fragment_repository_details

    override fun setUpUi() {
        repository = args.repository
        setToolbarNavigationIconOnClickListener()
        setToolbarTitle()
        setWebViewContent()
    }

    private fun setToolbarTitle(){
        binding.toolbar.title = repository.name
    }

    private fun setWebViewContent(){
        binding.webView.loadUrl(repository.repositoryUrl)
    }

    private fun setToolbarNavigationIconOnClickListener(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}