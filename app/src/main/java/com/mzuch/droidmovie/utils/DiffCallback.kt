package com.mzuch.droidmovie.utils

import androidx.recyclerview.widget.DiffUtil

fun <T : Equatable> getDiffCallback(compare: ((oldItem: T, newItem: T) -> Boolean)? = null) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
            compare?.invoke(oldItem, newItem) ?: false

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }