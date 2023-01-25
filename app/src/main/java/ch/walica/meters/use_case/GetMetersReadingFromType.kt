package ch.walica.meters.use_case

import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.domain.repository.MeterReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetersReadingFromType @Inject constructor(private val repository: MeterReadingRepository) {

    operator fun invoke(type: String): Flow<List<MeterReading>> =
        repository.getMetersReadingFromType(type).map { meterReadings ->
            meterReadings.sortedByDescending { it.date }
        }
}