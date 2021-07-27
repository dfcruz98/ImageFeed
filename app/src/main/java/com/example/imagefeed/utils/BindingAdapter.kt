package com.example.imagefeed.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.imagefeed.R


@BindingAdapter(
    "glide",
    "cornerRadius",
    requireAll = false
)
fun ImageView.bindGlide(
    url: String,
    radius: Int?
) {

    val glide = Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_image_24)

    if (radius != null && radius > 0) {
        glide.transform(CenterCrop(), RoundedCorners(radius))
    } else {
        glide.transform(CenterCrop())
    }

    glide.into(this)
}
