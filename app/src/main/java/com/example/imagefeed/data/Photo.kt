package com.example.imagefeed.data

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val public: Boolean,
    val friend: Boolean,
    val family: Boolean,
) {
    fun getUrl() = "http://farm${farm}.staticflickr.com/$server/${id}_${secret}.jpg"
}