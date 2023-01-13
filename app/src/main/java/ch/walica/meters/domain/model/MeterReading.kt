package ch.walica.meters.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "meters")
data class MeterReading(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "reading") val reading: Int,
    @ColumnInfo(name = "date") val date: ZonedDateTime = ZonedDateTime.now()
)