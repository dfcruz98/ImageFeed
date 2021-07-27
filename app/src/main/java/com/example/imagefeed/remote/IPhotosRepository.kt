package com.example.imagefeed.remote

import com.example.imagefeed.data.Photo
import com.example.imagefeed.utils.ActionResult

interface IPhotosRepository {
    suspend fun getPhotos(tag: String, number: Int): ActionResult<List<Photo>>
}