package com.example.imagefeed.remote

import com.example.imagefeed.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {
    @GET("rest/")
    suspend fun searchPhotos(
        @Query("method") method: String,
        @Query("tags") tags: String,
        @Query("per_page") perPage: Int
    ): Response<SearchResponse>
}