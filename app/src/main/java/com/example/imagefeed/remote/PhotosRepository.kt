package com.example.imagefeed.remote

import com.example.imagefeed.data.Photo
import com.example.imagefeed.utils.ActionResult
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val api: PhotosService
) : IPhotosRepository {

    /**
     * Get list of photos
     *
     * @param tag
     * @param number number of photos to get
     */
    override suspend fun getPhotos(tag: String, number: Int): ActionResult<List<Photo>> {
        return try {
            val response = api.searchPhotos("flickr.photos.search", tag, number)
            val content = response.body()
            if (response.isSuccessful && content != null) {
                ActionResult.Success(content.response!!.photos)
            } else {
                ActionResult.Error(response.message())
            }
        } catch (ex: Exception) {
            ActionResult.Error(ex.message ?: "Occured an error")
        }
    }

}