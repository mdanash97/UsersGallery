package com.example.usersgallery.view.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.usersgallery.data.pojo.Photo
import com.example.usersgallery.data.remote.NetworkResult
import com.example.usersgallery.databinding.FragmentPhotosBinding
import com.example.usersgallery.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var photosBinding : FragmentPhotosBinding? = null
    private val binding get() = photosBinding!!
    private val viewModel : ViewModel by viewModels()
    private val args : PhotosFragmentArgs by navArgs()
    private val photoAdaptor by lazy {
        PhotosAdaptor(){
            var action = PhotosFragmentDirections.actionPhotosFragmentToPhotoView(it.url)
            findNavController().navigate(action)
        }
    }
    private lateinit var listToFilter : List<Photo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        photosBinding = FragmentPhotosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPhotos(args.albumData.id)
        observePhotosResponse()
        setPhotosRV()
        observeSearchView()

    }

    private fun observeSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {
        var filteredList = listToFilter.filter {
            it.title.startsWith(query.orEmpty(), ignoreCase = true)
        }
        photoAdaptor.submitList(filteredList)
    }

    private fun setPhotosRV() {
        binding.photoRV.apply {
            layoutManager = GridLayoutManager(requireContext(),4)
            adapter = photoAdaptor
        }
    }

    private fun observePhotosResponse() {
        viewModel.photos.observe(viewLifecycleOwner){result ->
            when(result){
                is NetworkResult.Loading -> {
                    binding.photoRV.visibility = View.GONE
                    binding.albumNameTV.visibility = View.GONE
                }
                is NetworkResult.Success -> {
                    binding.photoRV.visibility = View.VISIBLE
                    binding.albumNameTV.visibility = View.VISIBLE
                    binding.albumNameTV.text = args.albumData.title
                    result.data?.let {
                        photoAdaptor.submitList(it)
                        listToFilter = it
                    }
                }
                is NetworkResult.Error -> {
                    //network error
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        photosBinding = null
    }
}