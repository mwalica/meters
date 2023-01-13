package ch.walica.meters.use_case

import ch.walica.meters.domain.model.MeterReading
import ch.walica.meters.domain.repository.MeterReadingRepository
import javax.inject.Inject

class InsertMeterReading @Inject constructor(private val repository: MeterReadingRepository) {

    suspend operator fun invoke(meterReading: MeterReading) {
        repository.insertMeterReading(meterReading)
    }
}