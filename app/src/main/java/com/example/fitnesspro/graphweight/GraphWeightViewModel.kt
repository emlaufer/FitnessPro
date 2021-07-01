package com.example.fitnesspro.graphweight

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.fitnesspro.database.WeightDao
import com.example.fitnesspro.util.resample
import com.github.mikephil.charting.data.Entry
import java.util.*

class GraphWeightViewModel(weightTable: WeightDao): ViewModel() {

    val weights = weightTable.getAllWeights()
    val weightsEntries = Transformations.map(weights) { weights ->
        // TODO: make use double instead?
        // todo: kinda gross
        weights.map { weight -> Entry(weight.timestamp?.toEpochMilli()?.toFloat() ?: 0.0f, weight.weight.toFloat()) }
    }
    val weeklyEntries = Transformations.map(weightsEntries) { entries ->
        resample(entries, Calendar.WEEK_OF_YEAR)
    }
}