package com.example.fitnesspro.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

// TODO: validation?
@Entity(tableName = "weight_table")
data class WeightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    // just a double representing lbs for now. In future, allow conversions
    @ColumnInfo(name = "weight_lbs")
    var weight: Double = 0.0,

    // timestamp of when added
    @ColumnInfo(name = "utc_millis")
    var timestamp: Instant? = null,
)

class Converters {
    @TypeConverter
    fun fromUtcMillis(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun dateToUtcMillis(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }
}