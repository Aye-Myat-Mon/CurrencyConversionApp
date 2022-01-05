package com.paypay.android.test.currencyconversion.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.paypay.android.test.currencyconversion.model.Quote

/**
 * Created by ayemyatmon on 05,January,2022
 */
class CurrencyTypeConverters {
    @TypeConverter
    fun listToString(values: List<Double>): String {
        val strList = mutableListOf<String>()
        values.forEach {
            strList.add(it.toString())
        }
        return strList.joinToString(",")
    }

    @TypeConverter
    fun stringToList(value: String): List<Double> {
        val intList = mutableListOf<Double>()
        value.split(",").forEach {
            intList.add(it.toDouble())
        }
        return intList
    }

    @TypeConverter
    fun appToString(quote: Quote): String = Gson().toJson(quote)

    @TypeConverter
    fun stringToApp(string: String): Quote = Gson().fromJson(string, Quote::class.java)
}