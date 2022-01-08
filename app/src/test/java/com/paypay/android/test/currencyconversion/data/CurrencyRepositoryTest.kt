package com.paypay.android.test.currencyconversion.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.paypay.android.test.currencyconversion.Constants
import com.paypay.android.test.currencyconversion.TestData
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import com.paypay.android.test.currencyconversion.data.repository.CurrencyRepository
import com.paypay.android.test.currencyconversion.data.services.CurrencyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ayemyatmon on 07,January,2022
 */
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val currencyApi = mock<CurrencyService>()

    private val currencyDao = mock<CurrencyDao>()

    private lateinit var currencyRepository: CurrencyRepository

    private val mainThread = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
        currencyRepository = CurrencyRepository(currencyApi, currencyDao)
    }

    @Test
    fun testGetLiveCurrency_Success() = runBlocking {
        Mockito.`when`(currencyApi.getLiveCurrency(Constants.API_KEY, "USD"))
            .thenReturn(TestData.getCurrencyLiveSuccessResponse())

        val result = currencyRepository.getLiveCurrency(Constants.API_KEY, "USD").toList()

        Assert.assertEquals(result.last(), TestData.getCurrencyLiveSuccess())
        Assert.assertNotNull(result)
    }

    @Test
    fun testGetListCurrency_Success() = runBlocking {
        Mockito.`when`(currencyApi.getListCurrency(Constants.API_KEY))
            .thenReturn(TestData.getCurrencyListSuccessResponse())

        val result = currencyRepository.getListCurrency(Constants.API_KEY).toList()

        Assert.assertEquals(result.last(), TestData.getCurrencyListSuccess())
        Assert.assertNotNull(result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThread.cleanupTestCoroutines()
    }
}