package ch.walica.meters.data.repository

import ch.walica.meters.data.dao.MeterReadingDao
import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.domain.repository.MeterReadingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MeterReadingRepositoryImpl @Inject constructor(private val meterReadingDao: MeterReadingDao) :
    MeterReadingRepository {
    override fun getMetersReadingFromType(type: String): Flow<List<MeterReading>> =
        meterReadingDao.getMetersReadingFromType(type)

    override suspend fun insertMeterReading(meterReading: MeterReading) =
        meterReadingDao.insertMeterReading(meterReading)

    override suspend fun deleteMeterReading(meterReading: MeterReading) =
        meterReadingDao.deleteMeterReading(meterReading)
}