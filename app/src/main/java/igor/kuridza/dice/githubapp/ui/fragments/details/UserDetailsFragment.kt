package igor.kuridza.dice.githubapp.ui.fragments.details

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.OPEN_PROFILE
import igor.kuridza.dice.githubapp.common.OPEN_REPOSITORIES
import igor.kuridza.dice.githubapp.databinding.FragmentUserDetailsBinding
import igor.kuridza.dice.githubapp.model.User
import igor.kuridza.dice.githubapp.ui.fragments.base.BaseFragment

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    private val args: UserDetailsFragmentArgs by navArgs()
    private lateinit var user: User

    override fun getLayoutResourceId(): Int = R.layout.fragment_user_details

    override fun setUpUi() {
        user = args.user
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