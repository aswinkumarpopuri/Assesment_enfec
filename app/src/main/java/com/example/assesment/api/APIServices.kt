package com.example.assesment.api

import com.example.assesment.api.model.PostsResponseItem
import com.example.assesment.api.model.UsersResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIServices {

    @GET("users")
    suspend fun fetchUserDetails(): Response<ArrayList<UsersResponseItem>?>

    @GET("posts")
    suspend fun fetchPostsDetails(): Response<ArrayList<PostsResponseItem>?>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): APIServices {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIServices::class.java)
        }
    }

}