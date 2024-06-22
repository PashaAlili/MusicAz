package com.example.musicaz.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.musicaz.R
import com.example.musicaz.core.BaseRecyclerAdapter
import com.example.musicaz.core.BaseViewHolder
import com.example.musicaz.databinding.ArtistListBinding
import com.example.musicaz.di.Artist

class ArtistListRecycleViewAdapter : BaseRecyclerAdapter<BaseViewHolder<Artist>>() {
    private var listener: OnItemClickListener? = null
    private var listenerNew: OnItemClickListenerNew? = null

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    interface OnItemClickListenerNew {
        fun onItemClick(artist: Artist)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    fun setOnItemClickListenerNew(listener: OnItemClickListenerNew) {
        this.listenerNew = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean =
            try {
                oldItem.hashCode() == newItem.hashCode()
            } catch (e: Exception) {
                false
            }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var items: List<Artist>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Artist> {
        val itemView = ArtistListBinding.inflate(LayoutInflater.from(parent.context) ,parent, false)

        return ArtistListItemViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: BaseViewHolder<Artist>, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
            listenerNew?.onItemClick(items[position])
        }
    }



    inner class ArtistListItemViewHolder (private val binding: ArtistListBinding) :
        BaseViewHolder<Artist>(binding.root){
        override fun bind (artistListItem: Artist) {
            binding.tvArtistName.text = artistListItem.name
            Glide.with(binding.root.context)
                .load(artistListItem.image)
                .error(R.drawable.spotify)
                .into(binding.ivArtist)
        }

    }
}