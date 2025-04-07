package com.example.libraryui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class LibItemDiffCallback : DiffUtil.ItemCallback<LibraryItem>(){

    override fun areItemsTheSame(
        oldItem: LibraryItem,
        newItem: LibraryItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LibraryItem,
        newItem: LibraryItem
    ): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.imageID == newItem.imageID
    }

}