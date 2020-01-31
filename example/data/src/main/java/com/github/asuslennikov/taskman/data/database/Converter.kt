package com.github.asuslennikov.taskman.data.database

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object Converter {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): ZonedDateTime? =
        value?.let { time -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of("UTC")) }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(value: ZonedDateTime?): Long? =
        value?.let { zdt -> zdt.withZoneSameLocal(ZoneId.of("UTC")).toInstant().toEpochMilli() }

}