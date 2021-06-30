package com.example.fitnesspro.addweight

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesspro.database.WeightDao
import com.example.fitnesspro.database.WeightEntity
import com.example.fitnesspro.util.EventLiveData
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddWeightViewModel(val weightTable: WeightDao): ViewModel() {

    // todo: I don't think we need to move weightString or dateString as member variables, but
    //       maybe? Not sure about lifecycle issues...

    private var date = MutableLiveData<Long>(Date().time)
    val dateString = Transformations.map(date) {
        SimpleDateFormat("MM/dd/yyyy").format(Date(it))
    }


    // todo: make mutable and non-mutable versions
    val navigateToFirstFragment = EventLiveData()
    val showDateClicker = EventLiveData()

    // TODO: refactor this....update the data here instead of in views
    fun onAddWeightData(weightString: String, dateString: String) {
        // TODO: are there Data/Form validators in Android?
        // convert weight and date to correct types. If fails, show toast
        val weight = weightString.toDoubleOrNull()
        val date = SimpleDateFormat("MM/dd/yyyy").runCatching { parse(dateString) }.getOrNull()
        val timestamp = date?.time

        Log.e("AddWeightViewModel", "Failed to parse data! $weightString and $dateString")
        // if either are invalid, then show a toast or something
        if (weight == null || timestamp == null) {
            Log.e("AddWeightViewModel", "Failed to parse data! $weightString and $dateString")
            return
        }

        // insert the new weight into the database
        viewModelScope.launch {
            val weightEntity = WeightEntity(weight = weight, timestamp =  timestamp)
            weightTable.insert(weightEntity)
            navigateToFirstFragment.trigger()
        }
    }

    // when the date is clicked, show the date picker
    fun onDateClicked() {
        showDateClicker.trigger()
    }

    fun onDateChosen(timestamp: Long) {
        // update the timestamp
        date.value = timestamp
    }
}