package com.paypay.android.test.currencyconversion

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.paypay.android.test.currencyconversion.data.db.AppDatabase
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by ayemyatmon on 07,January,2022
 */
@RunWith(AndroidJUnit4::class)
class CurrencyDaoTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        currencyDao = database.currencyDao()
    }

    @Test
    fun insertLiveCurrency() {
        currencyDao.insertLive(TestCurrencyData.getCurrencyLiveObject())

        val result = currencyDao.getLiveCurrency()
        Assert.assertNotNull(result)
        Assert.assertEquals(TestCurrencyData.currencyObj.quotes, result.quotes)
    }

    @Test
    fun insertListCurrency() {
        currencyDao.insertList(TestCurrencyData.getCurrencyListObject())

        val result = currencyDao.getListCurrency()
        Assert.assertNotNull(result)
        Assert.assertEquals(TestCurrencyData.currencyListObj.currencies, result.currencies)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

}