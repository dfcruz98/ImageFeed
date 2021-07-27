package com.example.imagefeed.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("stat")
    val stat: String,
    @SerializedName("photos")
    val response: Response?
)

data class Response(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perPages: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("photo")
    val photos: List<Photo>
)