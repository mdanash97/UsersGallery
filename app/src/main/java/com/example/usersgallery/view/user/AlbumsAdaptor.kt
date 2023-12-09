package com.example.usersgallery.view.user

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usersgallery.data.pojo.Album
import com.example.usersgallery.databinding.AlbumItemBinding

class AlbumDiffUtil : DiffUtil.ItemCallback<Album>(){
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }
}

class AlbumViewHolder(var holderBinding : AlbumItemBinding): RecyclerView.ViewHolder(holderBinding.root)

class AlbumsAdaptor(private val onClick : (Album) -> Unit) : ListAdapter<Album, AlbumViewHolder>(
    AlbumDiffUtil()
) {

    lateinit var adaptorBinding: AlbumItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        adaptorBinding = AlbumItemBinding.inflate(inflater,parent,false)
        return AlbumViewHolder(adaptorBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val currentObj = getItem(position)
        holder.holderBinding.noTV.text = (position+1).toString()
        holder.holderBinding.albumNameTV.text = currentObj.title
        holder.holderBinding.rvItem.setOnClickListener {
            onClick(currentObj)
        }
    }
}