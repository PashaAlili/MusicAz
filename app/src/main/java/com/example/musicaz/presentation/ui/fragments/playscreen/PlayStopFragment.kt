package com.example.musicaz.presentation.ui.fragments.playscreen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.musicaz.R
import com.example.musicaz.databinding.FragmentPlayStopBinding
import com.example.musicaz.presentation.viewmodels.SelectedArtistSongs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayStopFragment : Fragment() {
    private lateinit var binding: FragmentPlayStopBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var index = 0

    private var currentPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayStopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAndRemoveMusic()
    }

    fun stopAndRemoveMusic() {
        mediaPlayer?.pause()
        currentPosition = mediaPlayer?.currentPosition ?: 0
        isPlaying = false
        mediaPlayer = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = requireArguments().getString(SONG_INDEX, "0").toInt()
        prepareViewComponents()
        playMusic()
    }



    fun prepareViewComponents() {
        binding.btnNext.setOnClickListener {

            if(index < SelectedArtistSongs.myList.count()) {

                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null

                playNextMusic()
            }
        }

        binding.btnEx.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            if(index > 0) {
                playExtMusic()
            }else {
                playMusic()
            }
        }

        binding.playPauseButton.setOnClickListener {
            if (!isPlaying) {
                mediaPlayer?.start()
                isPlaying = true
                binding.playPauseButton.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.pause)
                )
            } else {
                mediaPlayer?.pause()
                currentPosition = mediaPlayer?.currentPosition ?: 0
                isPlaying = false
                binding.playPauseButton.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.play)
                )
            }
        }

        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress * 1000)


                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        binding.shareButton.setOnClickListener {
            shareText(requireContext(), SelectedArtistSongs.myList[index].audio, "Share")
        }
    }

    fun playMusic() {

        val urlStr = SelectedArtistSongs.myList[index].audio
        val imgStr = SelectedArtistSongs.myList[index].image
        val title = SelectedArtistSongs.myList[index].name
        val albumName = SelectedArtistSongs.myList[index].albumName

        binding.songTitleTextview.text = title
        binding.artistNameTextview.text = albumName

        Glide.with(this).load(imgStr).into(binding.ivArtistPlay)

        mediaPlayer = MediaPlayer().apply {
            setDataSource(urlStr)
            prepareAsync()
            setOnPreparedListener {
                it.seekTo(currentPosition)
                it.start()
                this@PlayStopFragment.isPlaying = true
                binding.playPauseButton.setImageResource(R.drawable.pause)
                binding.seekBar.max = it.duration / 1000
            }
        }



        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    binding.seekBar.progress = it.currentPosition / 1000
                    val second = binding.seekBar.progress%60
                    val minute = binding.seekBar.progress/60
                    val minuteStr = if(minute < 10) "0$minute" else "$minute"
                    val secondStr = if(second < 10) "0$second" else "$second"
                    binding.textTime.text = "${minuteStr}:${secondStr}"
                }
                handler.postDelayed(this, 1000)


            }
        }
        handler.postDelayed(runnable, 1000)
    }

    fun playNextMusic() {
        index +=1
        playMusic()
    }

    fun playExtMusic() {
        index -=1
        playMusic()
    }


    companion object {
        const val SONG_INDEX = "SONG_INDEX"
    }

    fun shareText(context: Context, text: String, title: String = "Paylaş") {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, title))
    }
}

