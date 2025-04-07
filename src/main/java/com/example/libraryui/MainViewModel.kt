package com.example.libraryui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _notes = MutableLiveData<List<String>>()
    val notes: LiveData<List<String>> = _notes

    fun updateNotes(list: List<String>) {
        val oldList = _notes.value
        _notes.value = oldList?.plus((list)) ?: list
    }
}