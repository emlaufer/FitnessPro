package com.example.fitnesspro.first

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesspro.database.WeightDao
import com.example.fitnesspro.database.WeightEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class FirstViewModel(val weightTable: WeightDao): ViewModel() {
    // TODO: take in Date instead?
    fun onClear() {
        viewModelScope.launch {
            weightTable.clear()
        }
    }
}