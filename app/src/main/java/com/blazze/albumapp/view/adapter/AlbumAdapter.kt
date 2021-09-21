package com.blazze.albumapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blazze.albumapp.R
import com.blazze.albumapp.databinding.ListItemAlbumBinding
import com.blazze.albumapp.model.Album

class AlbumAdapter(private val albumList: List<Album>) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_album,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindItems()
    }

    override fun getItemCount(): Int = albumList.size

    inner class AlbumViewHolder(private val binding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems() {
            binding.textAlbumTitle.text = albumList[bindingAdapterPosition].title
        }
    }


}