package com.example.usersgallery.view.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.databinding.PhotosItemBinding

class PhotosDiffUtil : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
}

class PhotoViewHolder(var holderBinding : PhotosItemBinding) : RecyclerView.ViewHolder(holderBinding.root)

class PhotosAdaptor(private val onClick : (Photo) -> Unit) : ListAdapter<Photo, PhotoViewHolder>(
    PhotosDiffUtil()
){

    lateinit var adaptorBinding : PhotosItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater : LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        adaptorBinding = PhotosItemBinding.inflate(inflater,parent,false)
        return PhotoViewHolder(adaptorBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentObj =getItem(position)
        Glide.with(holder.itemView.context)
            .load(currentObj.thumbnailUrl)
            .into(holder.holderBinding.photoIV)
        holder.holderBinding.photoItem.setOnClickListener {
            onClick(currentObj)
        }
    }
}