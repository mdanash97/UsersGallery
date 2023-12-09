package com.example.usersgallery.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.usersgallery.R
import com.example.usersgallery.databinding.FragmentPhotoViewBinding

class PhotoView : Fragment() {

    private var photosBinding: FragmentPhotoViewBinding? = null
    private val binding get() = photosBinding!!
    private val args : PhotoViewArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        photosBinding = FragmentPhotoViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage(args.photoURL)
    }

    private fun setImage(photoURL: String) {
        Glide.with(this)
            .load(photoURL)
            .into(binding.photoViewHolder)
    }

    override fun onDestroy() {
        super.onDestroy()
        photosBinding = null

    }
}