package com.example.usersgallery.view.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersgallery.data.pojo.User
import com.example.usersgallery.data.remote.NetworkResult
import com.example.usersgallery.databinding.FragmentUserBinding
import com.example.usersgallery.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var userBinding : FragmentUserBinding? = null
    private val binding get() = userBinding!!
    private val viewModel : ViewModel by viewModels()
    private val userId : Long = 4
    private val args : UserFragmentArgs by navArgs()
    private val albumsAdaptor by lazy {
        AlbumsAdaptor(){
            var action = UserFragmentDirections.actionUserFragmentToPhotosFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userBinding = FragmentUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAlbums(args.userData.id)
        observeAlbums(args.userData)
        setAlbumsRV()
    }

    private fun updateUI(user: User) {
        binding.apply {
            progressBar.visibility = View.GONE
            tv1.visibility = View.VISIBLE
            tv2.visibility = View.VISIBLE
            tv3.visibility = View.VISIBLE
            tv4.visibility = View.VISIBLE
            nameTV.visibility = View.VISIBLE
            mobileTV.visibility = View.VISIBLE
            addressTV.visibility = View.VISIBLE
            profileTV.visibility = View.VISIBLE
            albumsRV.visibility = View.VISIBLE
        }
        setUser(user)
    }

    private fun observeAlbums(user: User){
        viewModel.albums.observe(viewLifecycleOwner){result ->
            when(result){
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    updateUI(user)
                    result.data?.let {
                        albumsAdaptor.submitList(it)
                    }
                }
                is NetworkResult.Error -> {
                    //network error
                }
            }

        }
    }

    private fun setUser(user: User?) {
        binding.apply {
            if (user != null){
                nameTV.text = user.name
                addressTV.text = user.address.suite+ ", " + user.address.street + ", " + user.address.city
                mobileTV.text = user.phone
            }
        }
    }

    private fun setAlbumsRV(){
        binding.albumsRV.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = albumsAdaptor
            hasFixedSize()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        userBinding = null
    }

}