package igor.kuridza.dice.githubapp

import android.app.Application
import igor.kuridza.dice.githubapp.di.networkingModule
import igor.kuridza.dice.githubapp.di.repositoryModule
import igor.kuridza.dice.githubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GithubApp)
            modules(listOf(
                viewModelModule,
                networkingModule,
                repositoryModule
            ))
        }
    }

}