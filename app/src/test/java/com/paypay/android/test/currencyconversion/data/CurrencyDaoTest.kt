package com.paypay.android.test.currencyconversion.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.paypay.android.test.currencyconversion.TestData
import com.paypay.android.test.currencyconversion.data.db.AppDatabase
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ayemyatmon on 07,January,2022
 */

@RunWith(MockitoJUnitRunner::class)
class CurrencyDaoTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = mock<AppDatabase>()
    private lateinit var currencyDao: CurrencyDao

    @Before
    fun setUp() {
        currencyDao = database.currencyDao()
    }

    @Test
    fun insertLiveCurrency() {
        currencyDao.insertLive(TestData.getCurrencyLiveObject())
        verify(currencyDao).insertLive(TestData.getCurrencyLiveObject())

        val result = currencyDao.getLiveCurrency()
        Assert.assertEquals(result, TestData.getCurrencyLiveObject())
        Assert.assertNotNull(result)
        Assert.assertEquals(CurrencyModel::class.java, result)
    }

    @Test
    fun insertListCurrency() {
        currencyDao.insertList(TestData.getCurrencyListObject())
        verify(currencyDao).insertList(TestData.getCurrencyListObject())

        val result = currencyDao.getListCurrency()
        Assert.assertEquals(result, TestData.getCurrencyListObject())
        Assert.assertNotNull(result)
        Assert.assertEquals(CurrencyListModel::class.java, result)
    }

    @After
    fun tearDown() {
        database.close()
    }
}