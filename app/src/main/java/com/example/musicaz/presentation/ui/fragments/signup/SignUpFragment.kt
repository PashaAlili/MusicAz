package com.example.musicaz.presentation.ui.fragments.signup

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentSignUpBinding
import com.example.musicaz.presentation.viewmodels.ErrorType
import com.example.musicaz.presentation.viewmodels.Gender
import com.example.musicaz.presentation.viewmodels.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(), View.OnClickListener,View.OnFocusChangeListener,View.OnKeyListener {

    private lateinit var binding : FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container,false)
        auth = FirebaseAuth.getInstance()
        viewModel = SignUpViewModel()
        val items =  viewModel.genders()
        val adapter = ArrayAdapter<Gender>(requireContext(), R.layout.list_item_gender, R.id.customGenderTextView, items)
        binding.progress.visibility = View.GONE

        binding.dropdownField.setAdapter(adapter)
        binding.dropdownField.setOnItemClickListener { parent, view, position, id ->
            viewModel.selectedGender = items[position]
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)


        binding.btNext.setOnClickListener {
            if (viewModel.hasErrorMessageWithType().second) {
                val errorType = viewModel.hasErrorMessageWithType().first
                var message = errorType.toString()
                if(errorType == ErrorType.EMAIL) {
                    binding.editTextEmail.backgroundTintList = ColorStateList.valueOf(Color.RED)
                }else if(errorType == ErrorType.PASSWORD) {
                    binding.editTextPassword.backgroundTintList = ColorStateList.valueOf(Color.RED)
                }else {
                    message = "Bilinmeyen hata"
                }

                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            else {
                binding.progress.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(viewModel.email, viewModel.password)
                    .addOnCompleteListener {
                        binding.progress.visibility = View.GONE
                        if(it.isSuccessful) {
                            val navController = findNavController()
                            navController.navigate(com.example.musicaz.R.id.action_signUpFragment_to_signUpNameFragment)
                        }else {
                            Toast.makeText(context, "Kayıt başarısız. Hata: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }


            }

        }

        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.email = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.password = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onClick(v: View?) {

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {

    }

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return true
    }

}

