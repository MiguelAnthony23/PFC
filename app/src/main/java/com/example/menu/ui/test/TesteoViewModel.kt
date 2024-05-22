package com.example.menu.ui.test


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TesteoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Página anterior"
    }
    val text: LiveData<String> = _text
}