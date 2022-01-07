package com.paypay.android.test.currencyconversion

import com.google.gson.JsonParser
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by ayemyatmon on 08,January,2022
 */
object TestData {
    val quoteStr =
        "{\"USDAED\":3.67296,\"USDAFN\":104.910141,\"USDALL\":107.404999,\"USDAMD\":483.532139,\"USDANG\":1.804153}"
    val currencyStr =
        "{\n" +
                "        \"AED\": \"United Arab Emirates Dirham\",\n" +
                "        \"AFN\": \"Afghan Afghani\",\n" +
                "        \"ALL\": \"Albanian Lek\",\n" +
                "        \"AMD\": \"Armenian Dram\",\n" +
                "        \"ANG\": \"Netherlands Antillean Guilder\",\n" +
                "        \"AOA\": \"Angolan Kwanza\",\n" +
                "        \"ARS\": \"Argentine Peso\",\n" +
                "        \"AUD\": \"Australian Dollar\",\n" +
                "        \"AWG\": \"Aruban Florin\",\n" +
                "        \"AZN\": \"Azerbaijani Manat\",\n" +
                "        \"BAM\": \"Bosnia-Herzegovina Convertible Mark\",\n" +
                "        \"BBD\": \"Barbadian Dollar\",\n" +
                "        \"BDT\": \"Bangladeshi Taka\",\n" +
                "        \"BGN\": \"Bulgarian Lev\"\n" +
                "}"

    val currencyObj = CurrencyModel(
        success = true,
        source = "USD",
        quotes = JsonParser().parse(quoteStr).asJsonObject
    )

    val currencyListObj = CurrencyListModel(
        success = true,
        currencies = JsonParser().parse(currencyStr).asJsonObject
    )

    fun getCurrencyLiveSuccess(): Flow<Result<CurrencyModel>> {
        return flow {
            Result.Success(currencyObj)
        }
    }

    fun getCurrencyListSuccess(): Flow<Result<CurrencyListModel>> {
        return flow {
            Result.Success(currencyListObj)
        }
    }
}