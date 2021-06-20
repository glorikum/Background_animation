package com.onix.internship.backgroundanimation.ui.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.onix.internship.backgroundanimation.BuildConfig
import com.onix.internship.backgroundanimation.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FirstFragmentBinding
    private val viewModel: FirstViewModel by viewModels {
        FirstViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.navigationLiveEvent.observe(viewLifecycleOwner, ::navigate)
        viewModel.animationOn(binding.backgroundOne, binding.backgroundTwo)
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}