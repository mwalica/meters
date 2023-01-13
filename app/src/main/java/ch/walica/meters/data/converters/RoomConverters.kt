package ch.walica.meters.data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class RoomConverters {

    @TypeConverter
    fun convertDateToLong(date: ZonedDateTime): Long = date.toEpochSecond()

    @TypeConverter
    fun convertLongToDate(timestamp: Long): ZonedDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
}