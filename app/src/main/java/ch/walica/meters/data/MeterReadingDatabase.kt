package ch.walica.meters.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.walica.meters.data.converters.RoomConverters
import ch.walica.meters.data.dao.MeterReadingDao
import ch.walica.meters.domain.model.MeterReading

@Database(entities = [MeterReading::class], version = 1, exportSchema = false)

@TypeConverters(RoomConverters::class)
abstract class MeterReadingDatabase : RoomDatabase() {
    abstract fun meterReadingDao(): MeterReadingDao
}