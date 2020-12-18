package igor.kuridza.dice.githubapp.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.githubapp.common.OPEN_PROFILE
import igor.kuridza.dice.githubapp.common.OPEN_REPOSITORIES
import igor.kuridza.dice.githubapp.databinding.FragmentUserDetailsBinding
import igor.kuridza.dice.githubapp.model.User

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val args: UserDetailsFragmentArgs by navArgs()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = args.user
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
        binding.toolbar.title = user.name
    }

    private fun setWebViewContent(){
        when(args.action){
            OPEN_PROFILE -> loadUrl(user.profileUrl)
            OPEN_REPOSITORIES -> loadUrl("${user.profileUrl}?tab=repositories")
        }
    }

    private fun loadUrl(url: String){
        binding.webView.loadUrl(url)
    }

    private fun setToolbarNavigationIconOnClickListener(){
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}