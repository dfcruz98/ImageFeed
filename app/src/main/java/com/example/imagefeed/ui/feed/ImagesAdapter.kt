package com.example.imagefeed.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.imagefeed.data.Photo
import com.example.imagefeed.databinding.ContentImageBinding

/**
 * This is adapter is responsible for the horizontal images
 */
class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private var itemsList = mutableListOf<Photo>()

    class ViewHolder(
        private val binding: ContentImageBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, photo: Photo) {
            binding.run {
                this.photo = photo
                this.root.setOnClickListener(listener)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemsList[position].let { photo ->
            with(holder) {
                bind(clickListener(photo), photo)
            }
        }
    }

    override fun getItemCount() = itemsList.size

    fun newList(items: MutableList<Photo>) {
        itemsList = items
        notifyDataSetChanged()
    }

    /**
     * Create a listener to detect when am image is selected,
     * and opens the image details fragment
     */
    private fun clickListener(photo: Photo): View.OnClickListener {
        return View.OnClickListener { view ->
            // Pass arguments to navigation
            val action = FeedFragmentDirections.navigateToDetails(
                photo.id,
                photo.getUrl(),
                photo.title
            )

            // Opens image details fragment
            view.findNavController().navigate(action)
        }
    }
}