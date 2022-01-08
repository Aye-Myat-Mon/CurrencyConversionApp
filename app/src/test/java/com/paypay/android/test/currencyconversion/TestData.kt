package com.paypay.android.test.currencyconversion

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.utils.Result
import retrofit2.Response

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

    val currencyObj: CurrencyModel = CurrencyModel(
        success = true,
        source = "USD",
        quotes = JsonParser().parse(quoteStr).asJsonObject
    )

    val currencyListObj = CurrencyListModel(
        success = true,
        currencies = JsonParser().parse(currencyStr).asJsonObject
    )

    fun getCurrencyLiveSuccessResponse(): Response<CurrencyModel> = Response.success(currencyObj)

    fun getCurrencyLiveObject(): CurrencyModel {
        return currencyObj
    }

    fun getCurrencyLiveSuccess(): Result<CurrencyModel> {
        return Result.Success(currencyObj)
    }

    fun getFlowLiveSuccess(): JsonArray {
        val flowLiveResult =
            "[Success(data=null), Loading, Success(data=CurrencyModel(source=USD, success=true, quotes={\"USDAED\":3.67296,\"USDAFN\":104.910141,\"USDALL\":107.404999,\"USDAMD\":483.532139,\"USDANG\":1.804153}))]"
        val flowLiveArr = JsonParser().parse(flowLiveResult).asJsonArray
        return flowLiveArr
    }

    fun getCurrencyListSuccessResponse(): Response<CurrencyListModel> =
        Response.success(currencyListObj)

    fun getCurrencyListObject(): CurrencyListModel {
        return currencyListObj
    }

    fun getCurrencyListSuccess(): Result<CurrencyListModel> {
        return Result.Success(currencyListObj)
    }

    fun getFlowListSuccess(): JsonArray {
        val flowListResult =
            "[Success(data=null), Loading, Success(data=CurrencyListModel(success=true, currencies={\"AED\":\"United Arab Emirates Dirham\",\"AFN\":\"Afghan Afghani\",\"ALL\":\"Albanian Lek\",\"AMD\":\"Armenian Dram\",\"ANG\":\"Netherlands Antillean Guilder\",\"AOA\":\"Angolan Kwanza\",\"ARS\":\"Argentine Peso\",\"AUD\":\"Australian Dollar\",\"AWG\":\"Aruban Florin\",\"AZN\":\"Azerbaijani Manat\",\"BAM\":\"Bosnia-Herzegovina Convertible Mark\",\"BBD\":\"Barbadian Dollar\",\"BDT\":\"Bangladeshi Taka\",\"BGN\":\"Bulgarian Lev\"}))]"
        val flowListArr = JsonParser().parse(flowListResult).asJsonArray
        return flowListArr
    }
}