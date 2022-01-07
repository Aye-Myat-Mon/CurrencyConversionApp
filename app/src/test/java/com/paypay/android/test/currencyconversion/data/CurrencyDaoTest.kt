package com.paypay.android.test.currencyconversion.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.gson.JsonParser
import com.paypay.android.test.currencyconversion.data.db.AppDatabase
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by ayemyatmon on 07,January,2022
 */

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class CurrencyDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var currencyDao: CurrencyDao

    @Before
    fun setup() {
        hiltRule.inject()
        currencyDao = database.currencyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val quoteStr =
            "{\"USDAED\":3.67296,\"USDAFN\":104.910141,\"USDALL\":107.404999,\"USDAMD\":483.532139,\"USDANG\":1.804153}"
        val currencyObj = CurrencyModel(
            success = true,
            source = "USD",
            quotes = JsonParser().parse(quoteStr).asJsonObject
        )
        /*currencyDao.insertAll(currencyObj)
        val currency = currencyDao.getAll()
        assertThat(currency).isEqualTo(currencyObj)*/
    }
}