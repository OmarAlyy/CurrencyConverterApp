package com.omaraly.currency.db

import androidx.room.*
import com.omaraly.currency.db.entity.RateList

@Dao
interface RateDao {


    @Query("select * from ratelist")
    fun getRates(): RateList?


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateRates(rateList: RateList)

}