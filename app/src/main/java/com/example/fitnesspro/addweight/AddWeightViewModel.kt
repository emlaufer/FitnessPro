package com.example.fitnesspro.addweight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesspro.database.WeightDao
import com.example.fitnesspro.database.WeightEntity
import com.example.fitnesspro.util.EventLiveData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.util.*

class AddWeightViewModel(val weightTable: WeightDao): ViewModel() {

    // todo: I don't think we need to move weightString or dateString as member variables, but
    //       maybe? Not sure about lifecycle issues...

    val weightString = MutableLiveData("")
    private var date = MutableLiveData(ZonedDateTime.now())
    val dateString = Transformations.map(date) {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        formatter.format(it)
    }


    // todo: make mutable and non-mutable versions
    val navigateToFirstFragment = EventLiveData()
    val showDateClicker = EventLiveData()

    fun onAddWeightData() {
        val weight = weightString.value?.toDoubleOrNull() ?: run {
            // show error toast
            return
        }
        val date = date.value ?: run {
            // show error toast
            return
        }

        // insert the new weight into the database
        viewModelScope.launch {
            val weightEntity = WeightEntity(weight = weight, timestamp = date.toInstant())
            weightTable.insert(weightEntity)
            navigateToFirstFragment.trigger()
        }
    }

    // when the date is clicked, show the date picker
    fun onDateClicked() {
        showDateClicker.trigger()
    }

    fun onDateChosen(aDate: ZonedDateTime) {
        // update the timestamp
        // TODO: right now, this is a utc timestamp.
        date.value = aDate
    }
}