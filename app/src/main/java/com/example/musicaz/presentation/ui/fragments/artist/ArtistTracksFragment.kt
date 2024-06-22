package com.example.musicaz.presentation.ui.fragments.artist

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentArtistTracksBinding

import com.example.musicaz.di.Artist
import com.example.musicaz.di.ArtistTrack
import com.example.musicaz.presentation.ui.adapters.ArtistListRecycleViewAdapter
import com.example.musicaz.presentation.ui.adapters.ArtistTracksRecycleViewAdapter
import com.example.musicaz.presentation.ui.fragments.playscreen.PlayStopFragment
import com.example.musicaz.presentation.viewmodels.AllArtistViewModel
import com.example.musicaz.presentation.viewmodels.ArtistTracksViewModel
import com.example.musicaz.presentation.viewmodels.SelectedArtistSongs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistTracksFragment : Fragment() {

    private lateinit var binding: FragmentArtistTracksBinding
    private lateinit var adapter: ArtistTracksRecycleViewAdapter
    private val viewModel: ArtistTracksViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistTracksBinding.inflate(inflater, container, false)
        binding.tvChooseArtistTrack.text = requireArguments().getString(ARTIST_TRACK, "")
        prepareAdapter()
        fetchDataSources()
        return binding.root
    }

    fun prepareAdapter() {
        adapter = ArtistTracksRecycleViewAdapter()
        binding.recycleViewArtistTrack.adapter = adapter
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recycleViewArtistTrack.layoutManager = layoutManager

        viewModel.artists.observe(viewLifecycleOwner) { artists ->
            artists?.let {
                adapter.items = it
                SelectedArtistSongs.addItem(it)
                adapter.setOnItemClickListener(object : ArtistTracksRecycleViewAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        val nav = findNavController()
                        nav.navigate(
                            R.id.action_artistTracksFragment_to_playStopFragment,
                            bundleOf(
                                PlayStopFragment.SONG_INDEX to position.toString()
                            )
                        )

                    }
                })
            }
        }
    }

    private fun fetchDataSources() {

        if(SelectedArtistSongs.myList.isEmpty()) {
            viewModel.fetchArtistTracks(requireArguments().getString(ARTIST_TRACK, ""))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    companion object {
        const val ARTIST_TRACK = "ARTIST_TRACK"
    }
}


