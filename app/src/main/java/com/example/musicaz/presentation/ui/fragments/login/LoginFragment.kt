package com.example.musicaz.presentation.ui.fragments.login

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.musicaz.localstorage.User
import com.example.musicaz.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.progress.visibility = View.GONE
        auth = FirebaseAuth.getInstance()
        binding.etForPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.etForPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!User.readLastUserEmail(requireContext()).isNullOrBlank()) {
            binding.etForLogin.setText(User.readLastUserEmail(requireContext())!!)
        }

        binding.buttonLogInn.setOnClickListener {
            binding.progress.isVisible = true
            auth.signInWithEmailAndPassword(binding.etForLogin.text.toString(), binding.etForPassword.text.toString())
                .addOnCompleteListener {
                    binding.progress.visibility = View.GONE
                    if(it.isSuccessful) {
                        if(binding.rememberMeCheckbox.isChecked) {
                            User.saveUser(requireContext(), binding.etForLogin.text.toString(), auth.uid.toString())
                        }else {
                            User.deleteUser(requireContext())
                        }
                        val navController = findNavController()
                        navController.navigate(com.example.musicaz.R.id.action_loginFragment_to_artistFragment)
                    }else {
                        Toast.makeText(context, "Login başarısız. Hata: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }




}