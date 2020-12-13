package igor.kuridza.dice.githubapp.di

import igor.kuridza.dice.githubapp.ui.fragments.repositories.RepositoriesListViewModel
import igor.kuridza.dice.githubapp.ui.fragments.search.SearchFragmentViewModel
import igor.kuridza.dice.githubapp.ui.fragments.users.UsersListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchFragmentViewModel() }
    viewModel { RepositoriesListViewModel(get()) }
    viewModel { UsersListViewModel(get()) }
}