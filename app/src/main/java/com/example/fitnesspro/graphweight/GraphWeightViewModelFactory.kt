package com.example.fitnesspro.graphweight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesspro.database.WeightDao
import java.lang.IllegalArgumentException

class GraphWeightViewModelFactory(
    private val dataSource: WeightDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GraphWeightViewModel::class.java)) {
            return GraphWeightViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}
