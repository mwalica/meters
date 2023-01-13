package ch.walica.meters.data.dao

import androidx.room.*
import ch.walica.meters.domain.model.MeterReading
import kotlinx.coroutines.flow.Flow


@Dao
interface MeterReadingDao {

    @Query("SELECT * FROM meters WHERE type = :type")
    fun getMetersReadingFromType(type: String): Flow<List<MeterReading>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeterReading(meterReading: MeterReading)

    @Delete
    suspend fun deleteMeterReading(meterReading: MeterReading)
}