package ch.walica.meters.domain.repository

import ch.walica.meters.domain.model.MeterReading
import kotlinx.coroutines.flow.Flow

interface MeterReadingRepository {
    fun getMetersReadingFromType(type: String): Flow<List<MeterReading>>
    suspend fun insertMeterReading(meterReading: MeterReading)
    suspend fun deleteMeterReading(meterReading: MeterReading)
}