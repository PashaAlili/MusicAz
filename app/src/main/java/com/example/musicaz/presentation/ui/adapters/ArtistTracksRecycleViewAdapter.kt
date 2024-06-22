package com.example.musicaz.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.musicaz.core.BaseRecyclerAdapter
import com.example.musicaz.core.BaseViewHolder
import com.example.musicaz.databinding.ArtistTrackItemBinding
import com.example.musicaz.di.ArtistTrack

class ArtistTracksRecycleViewAdapter : BaseRecyclerAdapter<BaseViewHolder<ArtistTrack>>() {
    private var listener: OnItemClickListener? = null

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ArtistTrack>() {
        override fun areItemsTheSame(oldItem: ArtistTrack, newItem: ArtistTrack): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArtistTrack, newItem: ArtistTrack): Boolean =
            try {
                oldItem.hashCode() == newItem.hashCode()
            } catch (e: Exception) {
                false
            }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<ArtistTrack>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ArtistTrack> {
        val itemView = ArtistTrackItemBinding.inflate(LayoutInflater.from(parent.context) ,parent, false)

        return ArtistTracksItemViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: BaseViewHolder<ArtistTrack>, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }



    inner class ArtistTracksItemViewHolder (private val binding: ArtistTrackItemBinding) :
        BaseViewHolder<ArtistTrack>(binding.root){
        override fun bind (artistTracksItem: ArtistTrack) {
            binding.tvArtistTrackName.text = artistTracksItem.name
            Glide.with(binding.root.context).load(artistTracksItem.image).into(binding.ivArtistTrack)
        }

    }
}