package com.paypay.android.test.currencyconversion.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.paypay.android.test.currencyconversion.Constants
import com.paypay.android.test.currencyconversion.TestData
import com.paypay.android.test.currencyconversion.data.repository.CurrencyRepository
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by ayemyatmon on 07,January,2022
 */
@ObsoleteCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val currencyRepository: CurrencyRepository = mock()

    private lateinit var viewModel: MainViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MainViewModel(currencyRepository)
    }

    @Test
    fun testGetListCurrency_Success() = runBlockingTest {
        Mockito.`when`(currencyRepository.getListCurrency(Constants.API_KEY))
            .thenReturn(TestData.getCurrencyListSuccess())

        viewModel.getListCurrency()
        verify(currencyRepository).getListCurrency(Constants.API_KEY)

        viewModel.currencyListResult.observeForever {
            if (viewModel.currencyListResult.value != null) {
                val result = CurrencyListModel(
                    it.success,
                    it.currencies
                )
                Assert.assertEquals(result, TestData.currencyListObj)

                val currencyListObj = viewModel.currencyListResult.value?.currencies
                Assert.assertNotNull(currencyListObj)
            }
        }
    }

    @Test
    fun testGetLiveCurrency_Success() = runBlockingTest {
        Mockito.`when`(currencyRepository.getLiveCurrency("USD", Constants.API_KEY))
            .thenReturn(TestData.getCurrencyLiveSuccess())

        viewModel.getLiveCurrency("USD")
        verify(currencyRepository).getLiveCurrency("USD", Constants.API_KEY)

        viewModel.currencyLiveResult.observeForever {
            if (viewModel.currencyLiveResult.value != null) {
                val result = CurrencyListModel(
                    it.success,
                    it.quotes
                )
                Assert.assertEquals(result, TestData.currencyObj)

                val currencyLiveObj = viewModel.currencyLiveResult.value?.quotes
                Assert.assertNotNull(currencyLiveObj)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}