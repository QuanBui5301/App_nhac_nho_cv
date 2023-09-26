package com.example.appnhacnhocv

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CursorViewModel : ViewModel() {
    val currentCursor = MutableLiveData<Cursor>()
}