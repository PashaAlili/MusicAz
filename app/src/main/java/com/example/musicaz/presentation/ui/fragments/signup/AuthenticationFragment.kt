package com.example.musicaz.presentation.ui.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.musicaz.localstorage.User
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthenticationBinding.inflate(layoutInflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(User.hasUser(requireContext())) {
            val navController = findNavController()
            navController.navigate(R.id.action_authenticationFragment_to_artistFragment)
        }

        binding.btSignUp.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.signUpFragment)

        }

        binding.btLogin.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_authenticationFragment_to_loginFragment)
        }
    }






}