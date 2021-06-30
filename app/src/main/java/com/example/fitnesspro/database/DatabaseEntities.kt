package com.example.fitnesspro.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_table")
data class WeightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    // just a double representing lbs for now. In future, allow conversions
    @ColumnInfo(name = "weight_lbs")
    var weight: Double = 0.0,

    // timestamp of when added
    @ColumnInfo(name = "time_millis")
    var timestamp: Long = 0L,
)
