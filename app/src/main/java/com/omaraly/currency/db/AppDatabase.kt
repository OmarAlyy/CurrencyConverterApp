package com.omaraly.currency.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omaraly.currency.db.entity.RateList

@Database(entities = [RateList::class], version = 1, exportSchema = false)
@TypeConverters(RateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

}