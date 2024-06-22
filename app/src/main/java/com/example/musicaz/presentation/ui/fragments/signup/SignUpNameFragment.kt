package com.example.musicaz.presentation.ui.fragments.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentSignUpNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNameFragment : Fragment() {

    private lateinit var binding: FragmentSignUpNameBinding
    private lateinit var webViewTerms: WebView
    private lateinit var webViewPrivacy : WebView



    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpNameBinding.inflate(layoutInflater, container,false)
        return binding.root
    }


    private fun changeRadioButtonSelectedColor(rButton: RadioButton, color: Int) {
        val colorStateList = ContextCompat.getColorStateList(requireContext(), color)
        rButton.buttonTintList = colorStateList
    }

    @SuppressLint("ResourceType", "SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeRadioButtonSelectedColor(binding.radioOffers, R.color.selected_color)
        changeRadioButtonSelectedColor(binding.radioPurpose, R.color.selected_color)


        val view = binding.root

        webViewTerms = binding.webViewTerms
        webViewPrivacy = binding.webViewPrivacy

        val webSettings1 = webViewTerms.settings
        webSettings1.javaScriptEnabled = true

        val webSettings2 = webViewPrivacy.settings
        webSettings2.javaScriptEnabled = true




        webViewTerms.loadUrl("https://www.spotify.com/az-az/legal/end-user-agreement/plain/")
        webViewPrivacy.loadUrl("https://www.spotify.com/az-az/legal/privacy-policy/plain/")

        binding.btSignUp.setOnClickListener {
            val n = findNavController()
            n.navigate(R.id.action_signUpNameFragment_to_artistFragment)

        }
    }
}







