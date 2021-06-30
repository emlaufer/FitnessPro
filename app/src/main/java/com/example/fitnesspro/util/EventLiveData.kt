package com.example.fitnesspro.util

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class EventLiveData: MutableLiveData<Boolean?>() {

    override fun setValue(value: Boolean?) {
        // only call the handler if the value is true
        if (value == true) {
            super.setValue(value)
            done()
        }
    }

    fun trigger() {
        this.value = true
    }

    fun done() {
        this.value = null
    }
}