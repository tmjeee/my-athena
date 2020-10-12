package com.tmjee.myathena.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeBinding
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment constructor(): Fragment() {

    lateinit var binding: FragmentHomeBinding

    val activityViewModel: MainActivityViewModel by activityViewModels()
    val viewModel: HomeFragmentViewModel by viewModels()
    val args: HomeFragmentArgs by navArgs()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false);
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        return binding.root;
    }

}


