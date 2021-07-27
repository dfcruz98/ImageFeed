package com.example.imagefeed.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagefeed.data.Group
import com.example.imagefeed.databinding.ContentFeedGroupBinding

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var itemsList = mutableListOf<Group>()

    inner class ViewHolder(private val binding: ContentFeedGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imagesAdapter: ImagesAdapter = ImagesAdapter()

        fun bind(group: Group) {
            binding.run {
                this.group = group
                executePendingBindings()
            }

            binding.imagesRecyclerView.adapter = imagesAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentFeedGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = itemsList[position]
        holder.bind(group)
        holder.imagesAdapter.newList(group.photos.toMutableList())
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    /**
     * Add new list of items to the adapter
     * @param items
     */
    fun setNewList(items: Collection<Group>) {
        itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

}