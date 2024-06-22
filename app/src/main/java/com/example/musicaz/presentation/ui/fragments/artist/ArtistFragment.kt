package com.example.musicaz.presentation.ui.fragments.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicaz.localstorage.User
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentArtistBinding
import com.example.musicaz.di.Artist
import com.example.musicaz.presentation.ui.adapters.ArtistListRecycleViewAdapter
import com.example.musicaz.presentation.viewmodels.AllArtistViewModel
import com.example.musicaz.presentation.viewmodels.ArtistTracksViewModel
import com.example.musicaz.presentation.viewmodels.SelectedArtistSongs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtistFragment : Fragment() {


    private val viewModel : AllArtistViewModel by viewModels()
    private val newVM : ArtistTracksViewModel by viewModels()
    private lateinit var binding : FragmentArtistBinding
    private lateinit var adapter: ArtistListRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArtistBinding.inflate(inflater, container, false)
        binding.tvChooseArtist.text = "Artists"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOff.setOnClickListener {
            User.deleteUser(requireContext())
            findNavController().popBackStack(R.id.authenticationFragment, false)
        }

        adapter = ArtistListRecycleViewAdapter()
        binding.recycleViewArtist.adapter = adapter
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recycleViewArtist.layoutManager = layoutManager

        adapter.setOnItemClickListenerNew(object : ArtistListRecycleViewAdapter.OnItemClickListenerNew{
            override fun onItemClick(artist: Artist) {
                SelectedArtistSongs.removeItem()
                findNavController().navigate(R.id.action_artistFragment_to_artistTracksFragment, bundleOf(ArtistTracksFragment.ARTIST_TRACK to artist.name))
            }

        })

        lifecycleScope.launch {
            viewModel.fetchArtists()
            viewModel.artists.observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.items = it
//                    adapter.setOnItemClickListener(object : ArtistListRecycleViewAdapter.OnItemClickListener {
//                    override fun onItemClick(position: Int) {
//
//                        SelectedArtistSongs.removeItem()
//                        val clickedItem = it[position]
//                        val nav = findNavController()
//                        nav.navigate(R.id.action_artistFragment_to_artistTracksFragment, bundleOf(ArtistTracksFragment.ARTIST_TRACK to clickedItem.name))
//
//                    }
//
//                })
                }
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }
}