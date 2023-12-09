package com.example.usersgallery.data.repo

import com.example.usersgallery.data.pojo.Album
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.data.pojo.User
import retrofit2.Response

interface RepositoryInterface {
    suspend fun getUser(userId : Long) : Response<User>
    suspend fun getAlbums(userId : Long) : Response<List<Album>>
    suspend fun getPhotos(albumId : Int) : Response<List<Photo>>
}