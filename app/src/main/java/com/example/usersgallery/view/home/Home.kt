package com.example.usersgallery.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.usersgallery.data.remote.NetworkResult
import com.example.usersgallery.databinding.FragmentHomeBinding
import com.example.usersgallery.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private var homeBinding : FragmentHomeBinding? = null
    private val binding get() = homeBinding!!
    private val viewModel : ViewModel by viewModels()
    private val userAdaptor by lazy {
        UserAdaptor(){
            var action = HomeDirections.actionHome2ToUserFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
        observeUsersResponse()
        setUserRV()
    }

    private fun setUserRV() {
        binding.userRV.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = userAdaptor
        }
    }

    private fun observeUsersResponse() {
        viewModel.users.observe(viewLifecycleOwner){result ->
            when(result){
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    result.data?.let {
                        userAdaptor.submitList(it)
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
        homeBinding = null
    }
}