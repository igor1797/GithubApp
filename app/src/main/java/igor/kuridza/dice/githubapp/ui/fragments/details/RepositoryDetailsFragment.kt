package igor.kuridza.dice.githubapp.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.githubapp.databinding.FragmentRepositoryDetailsBinding
import igor.kuridza.dice.githubapp.model.Repository

class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding
    private val args: RepositoryDetailsFragmentArgs by navArgs()
    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repository = args.repository
        binding = FragmentRepositoryDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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