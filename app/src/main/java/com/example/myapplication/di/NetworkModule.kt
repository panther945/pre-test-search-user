package com.example.myapplication.di

import com.example.myapplication.repo.GitHubService
import com.example.myapplication.repo.SearchRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { createOkHttp() }
    single { createMoshi() }
    single { createRetrofit(get(), get()) }
    single { SearchRepository(get()) }
    single { createGithubService(get()) }
}

private const val BASE_URL = "https://api.github.com/"

private fun createMoshi() = Moshi.Builder().build()

private fun createOkHttp() = OkHttpClient.Builder().build()

private fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

private fun createGithubService(retrofit: Retrofit): GitHubService {
    return retrofit.create(GitHubService::class.java)
}