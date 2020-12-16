package igor.kuridza.dice.githubapp.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.githubapp.databinding.FragmentUserDetailsBinding
import igor.kuridza.dice.githubapp.model.RepositoryOwner

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val args: UserDetailsFragmentArgs by navArgs()
    private lateinit var repositoryOwner: RepositoryOwner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repositoryOwner = args.repositoryOwner
        binding = FragmentUserDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarNavigationIconOnClickListener()
        setToolbarTitle()
        setWebViewContent()
    }

    private fun setToolbarTitle(){
        binding.toolbar.title = repositoryOwner.ownerName
    }

    private fun setWebViewContent(){
        binding.webView.loadUrl(repositoryOwner.ownerUrl)
    }

    private fun setToolbarNavigationIconOnClickListener(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}