package com.example.myapplication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUsers(
    @Json(name = "items")
    val users: List<User>
)
