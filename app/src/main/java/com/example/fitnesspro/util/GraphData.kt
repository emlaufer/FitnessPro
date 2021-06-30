package com.example.fitnesspro.util

import com.github.mikephil.charting.data.Entry
import java.util.*

// TODO: make generic to resample graph entries
// TODO: best to operate on entry, or to operate on WeightEntity?
// TODO: make sure the fact its sorted is documented. MUST be sorted!
// TODO: Right now, we just show these at the "nextWeekTimestamp". Might want a better x-axis
fun resample(entries: List<Entry>, interval: Int): List<Entry> {
    if (entries.isEmpty()) {
        return listOf<Entry>()
    }

    val result = mutableListOf<Entry>()
    var sum: Float = entries[0].y
    var count: Long = 1
    var nextWeekTimestamp = getNextCalendarInterval(entries[0].x.toLong(), interval)

    for (i in 1 until entries.size) {
        val entry = entries[i]
        val entryTimestamp = entry.x.toLong()

        // precond: entries is sorted,
        // so simply check if the entry is within or beyond next week timestamp
        if (entryTimestamp >= nextWeekTimestamp) {
            // add current sum to results
            result.add(Entry(nextWeekTimestamp.toFloat(), sum/count))
            // start new week
            nextWeekTimestamp = getNextCalendarInterval(entries[i].x.toLong(), interval)
            // reset counters
            sum = 0.0f
            count = 0
        }

        // add current entry to average counts
        sum += entries[i].y
        count += 1
    }

    // add the last value
    result.add(Entry(nextWeekTimestamp.toFloat(), sum/count))

    return result
}

fun getNextCalendarInterval(timestamp: Long, interval: Int): Long {
    // todo: should we pass in calendar, or just re-get each time?
    val calendar = Calendar.getInstance(Locale.getDefault())

    calendar.timeInMillis = timestamp
    calendar.add(interval, 1)
    calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
    return calendar.timeInMillis
}
