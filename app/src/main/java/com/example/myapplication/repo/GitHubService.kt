package com.example.myapplication.repo

import com.example.myapplication.model.SearchUsers
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubService {
    @GET("/search/users")
    fun searchUsers(
        @Query("q") name: String,
        @Query("per_page") perPage: Int = PAGE_SIZE
    ): Single<Response<SearchUsers>>

    @GET
    fun searchUsersByUrl(@Url url: String): Single<Response<SearchUsers>>
}