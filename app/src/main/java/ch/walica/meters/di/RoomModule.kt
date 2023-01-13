package ch.walica.meters.di

import android.app.Application
import androidx.room.Room
import ch.walica.meters.data.MeterReadingDatabase
import ch.walica.meters.data.dao.MeterReadingDao
import ch.walica.meters.data.repository.MeterReadingRepositoryImpl
import ch.walica.meters.domain.repository.MeterReadingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun providesMeterReadingDatabase(app: Application): MeterReadingDatabase {
        return Room.databaseBuilder(
            app,
            MeterReadingDatabase::class.java,
            "meters_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepository(meterReadingDatabase: MeterReadingDatabase): MeterReadingRepository {
        return MeterReadingRepositoryImpl(meterReadingDatabase.meterReadingDao())
    }
}