package com.example.musicaz.core

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : BaseViewHolder<*>> : RecyclerView.Adapter<T>() {

    init {
        this.setHasStableIds(true)
    }
}