package com.example.assesment.api.model

data class PostsResponseItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)