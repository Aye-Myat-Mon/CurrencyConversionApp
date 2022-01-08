package com.paypay.android.test.currencyconversion.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paypay.android.test.currencyconversion.MainCoroutineScopeRule
import com.paypay.android.test.currencyconversion.TestData
import com.paypay.android.test.currencyconversion.data.repository.CurrencyRepository
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.utils.Result
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by ayemyatmon on 07,January,2022
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val currencyRepository = mockk<CurrencyRepository>()

    private lateinit var viewModel: MainViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = MainViewModel(currencyRepository)
    }

    @Test
    fun testGetListCurrency_Success() = runBlocking {
        /*Mockito.`when`(currencyRepository.getListCurrency(Constants.API_KEY).toList())
            .thenReturn(TestData.getCurrencyListSuccess())*/

        viewModel.getListCurrency()

        viewModel.currencyListResult.observeForever {
            if (viewModel.currencyListResult.value != null) {
                val result = CurrencyListModel(
                    it.success,
                    it.currencies
                )
                Assert.assertEquals(result, TestData.getCurrencyListObject())

                val currencyListObj = viewModel.currencyListResult.value?.currencies
                Assert.assertNotNull(currencyListObj)
                Assert.assertEquals(Result.Success::class.java, result)
                Assert.assertEquals(CurrencyListModel::class.java, currencyListObj)
            }
        }
    }

    @Test
    fun testGetLiveCurrency_Success() = runBlocking {
        /*Mockito.`when`(currencyRepository.getLiveCurrency("USD", Constants.API_KEY))
            .thenReturn(TestData.getCurrencyLiveSuccess())*/

        viewModel.getLiveCurrency("USD")

        viewModel.currencyLiveResult.observeForever {
            if (viewModel.currencyLiveResult.value != null) {
                val result = CurrencyListModel(
                    it.success,
                    it.quotes
                )
                Assert.assertEquals(result, TestData.getCurrencyLiveObject())

                val currencyLiveObj = viewModel.currencyLiveResult.value?.quotes
                Assert.assertNotNull(currencyLiveObj)
                Assert.assertEquals(Result.Success::class.java, result)
                Assert.assertEquals(CurrencyModel::class.java, currencyLiveObj)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}