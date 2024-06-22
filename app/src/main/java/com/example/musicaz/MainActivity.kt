package com.example.musicaz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.musicaz.R.id.artistFragment
import com.example.musicaz.extensions.setStatusBarColors
import com.example.musicaz.presentation.ui.fragments.artist.ArtistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setStatusBarColors(R.color.backgroundBlack)
//        var fr = supportFragmentManager.findFragmentById(R.id.artistFragment)
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerView, fr!!)
//            .commit()
    }

}