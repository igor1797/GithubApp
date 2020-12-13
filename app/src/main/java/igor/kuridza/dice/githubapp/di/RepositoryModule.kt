package igor.kuridza.dice.githubapp.di

import igor.kuridza.dice.githubapp.repositories.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SearchRepository(get()) }
}