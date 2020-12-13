package igor.kuridza.dice.githubapp.di

import igor.kuridza.dice.githubapp.networking.GithubApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.github.com/"

val networkingModule = module {

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single{
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single {
        get<Retrofit>().create(GithubApiService::class.java)
    }
}