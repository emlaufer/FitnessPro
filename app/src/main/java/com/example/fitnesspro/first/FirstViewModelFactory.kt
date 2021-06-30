package com.example.fitnesspro.first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesspro.addweight.AddWeightViewModel
import com.example.fitnesspro.database.WeightDao
import java.lang.IllegalArgumentException

class FirstViewModelFactory(
    private val dataSource: WeightDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirstViewModel::class.java)) {
            return FirstViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
