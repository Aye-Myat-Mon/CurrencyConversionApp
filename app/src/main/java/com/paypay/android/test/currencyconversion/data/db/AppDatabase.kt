package com.paypay.android.test.currencyconversion.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paypay.android.test.currencyconversion.model.CurrencyModel

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Database(entities = [CurrencyModel::class], version = 1)
@TypeConverters(CurrencyTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}