package com.example.usersgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersgallery.data.pojo.Album
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.data.pojo.User
import com.example.usersgallery.data.remote.NetworkResult
import com.example.usersgallery.data.repo.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: RepositoryInterface) : ViewModel(){
    private val _users : MutableLiveData<NetworkResult<List<User>>> = MutableLiveData()
    val users : LiveData<NetworkResult<List<User>>> = _users

    private val _user : MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val user : LiveData<NetworkResult<User>> = _user

    private val _albums: MutableLiveData<NetworkResult<List<Album>>> = MutableLiveData()
    val albums : LiveData<NetworkResult<List<Album>>> = _albums

    private val _photos: MutableLiveData<NetworkResult<List<Photo>>> = MutableLiveData()
    val photos : LiveData<NetworkResult<List<Photo>>> = _photos

    fun getUser(userId : Long) {
        _user.value = NetworkResult.Loading()
        viewModelScope.launch {
        val response = repository.getUser(userId)
        if(response.isSuccessful){
            response.body()?.let {
                _user.postValue(NetworkResult.Success(it))
            }
        } else {
            _user.postValue(NetworkResult.Error("error"))
        }
        }
    }

    fun getAlbums(userId: Long){
        _albums.value = NetworkResult.Loading()
        viewModelScope.launch {
            val response = repository.getAlbums(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    _albums.postValue(NetworkResult.Success(it))
                }
            } else {
                _albums.postValue(NetworkResult.Error("error"))
            }
        }
    }

    fun getPhotos(albumId : Int){
        _photos.value = NetworkResult.Loading()
        viewModelScope.launch {
            val response = repository.getPhotos(albumId)
            if (response.isSuccessful) {
                response.body()?.let {
                    _photos.postValue(NetworkResult.Success(it))
                }
            } else {
                _photos.postValue(NetworkResult.Error("error"))
            }
        }
    }

    fun getUsers(){
        _users.value = NetworkResult.Loading()
        viewModelScope.launch {
            val response = repository.getUsers()
            if (response.isSuccessful) {
                response.body()?.let {
                    _users.postValue(NetworkResult.Success(it))
                }
            } else {
                _users.postValue(NetworkResult.Error("error"))
            }
        }
    }
}