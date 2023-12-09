package com.example.usersgallery.data.repo

import com.example.usersgallery.data.pojo.Album
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.data.pojo.User
import com.example.usersgallery.data.remote.ApiService
import retrofit2.Response

class Repository(private val apiService: ApiService) : RepositoryInterface {
    override suspend fun getUser(userId: Long): Response<User> {
        return apiService.getUser(userId)
    }

    override suspend fun getAlbums(userId: Long): Response<List<Album>> {
        return apiService.getAlbums(userId)
    }

    override suspend fun getPhotos(albumId: Int): Response<List<Photo>> {
        return apiService.getImages(albumId)
    }
}