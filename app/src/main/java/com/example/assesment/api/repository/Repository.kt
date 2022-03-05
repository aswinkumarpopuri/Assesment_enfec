package com.example.assesment.api.repository

import com.example.assesment.api.APIServices
import com.example.assesment.api.model.PostsResponseItem
import com.example.assesment.api.model.UsersResponseItem
import java.lang.Exception

class Repository {
    private val api_services = APIServices.create()

    suspend fun userDetails() : ArrayList<UsersResponseItem>?{
        kotlin.runCatching {
            api_services.fetchUserDetails()
        }.onSuccess {
            return it.body()
        }.onFailure {
            return null
        }
        return null
    }

    suspend fun postDetails() : ArrayList<PostsResponseItem>?{
        kotlin.runCatching {
            api_services.fetchPostsDetails()
        }.onSuccess {
            return it.body()
        }.onFailure {
            return null
        }
        return null
    }
}