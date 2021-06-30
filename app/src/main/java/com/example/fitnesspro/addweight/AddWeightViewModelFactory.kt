package com.example.fitnesspro.addweight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesspro.database.WeightDao
import java.lang.IllegalArgumentException

class AddWeightViewModelFactory(
    private val dataSource: WeightDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddWeightViewModel::class.java)) {
            return AddWeightViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
