package com.example.usersgallery.data.pojo

import java.io.Serializable

data class Album(
    val userId: Int,
    val id: Int,
    val title: String
) : Serializable