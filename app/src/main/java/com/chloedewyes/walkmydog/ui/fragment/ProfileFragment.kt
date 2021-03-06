package com.chloedewyes.walkmydog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chloedewyes.walkmydog.R
import com.chloedewyes.walkmydog.adpater.DogAdapter
import com.chloedewyes.walkmydog.databinding.FragmentProfileBinding
import com.chloedewyes.walkmydog.ui.viewmodels.FirestoreViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FirestoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.rvDogs.adapter = DogAdapter()

        viewModel.selectUser()
        viewModel.selectDog()

        onClick()

    }

    private fun onClick(){
        binding.tvSignOut.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.signInFragment)
        }

        binding.ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.profilePersonFragment)
        }

        binding.btnAddDog.setOnClickListener {
            findNavController().navigate(R.id.profileDogFragment)
        }
    }

}