package igor.kuridza.dice.githubapp.di

import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesViewModel
import igor.kuridza.dice.githubapp.ui.fragments.startscreen.SearchFragmentViewModel
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchFragmentViewModel() }
    viewModel { RepositoriesViewModel(get()) }
    viewModel { UsersViewModel(get()) }
}