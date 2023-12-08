package com.example.usersgallery.data.remote

import com.example.usersgallery.data.pojo.Album
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.data.pojo.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Long) : Response<User>

    @GET("users/{id}/albums")
    suspend fun getAlbums(@Path("id") userId: Long) : Response<List<Album>>

    @GET("albums/{id}/photos")
    suspend fun getImages(@Path("id") albumId: Long) : Response<List<Photo>>
}