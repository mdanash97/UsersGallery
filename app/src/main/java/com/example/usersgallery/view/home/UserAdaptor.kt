package com.example.usersgallery.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usersgallery.data.pojo.User
import com.example.usersgallery.databinding.UserItemBinding

class UserDiffUtil : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
}

class UserViewHolder(var holderBinding: UserItemBinding) : RecyclerView.ViewHolder(holderBinding.root)

class UserAdaptor(private val onClick : (User) -> Unit) : ListAdapter<User, UserViewHolder>(
    UserDiffUtil()
) {

    lateinit var adaptorBinding :UserItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        adaptorBinding = UserItemBinding.inflate(inflater,parent,false)
        return UserViewHolder(adaptorBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentObj = getItem(position)
        holder.holderBinding.userNameTV.text = currentObj.name
        holder.holderBinding.emailTV.text = currentObj.email
        holder.holderBinding.userItem.setOnClickListener {
            onClick(currentObj)
        }
    }
}