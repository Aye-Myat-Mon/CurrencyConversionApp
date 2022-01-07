package com.paypay.android.test.currencyconversion.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paypay.android.test.currencyconversion.R
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.model.Quote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val viewModel by viewModels<MainViewModel>()

    var amount = 1.0
    var currency = "USD"
    var currencyValue = 1.0
    lateinit var currencyModel: CurrencyModel
    lateinit var currencyAdapter: CurrencyListAdapter
    private lateinit var quoteObjArr: ArrayList<Quote>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quoteObjArr = ArrayList()

        viewModel.getListCurrency()
        viewModel.getLiveCurrency(currency)

        //setup recycler
        currencyAdapter = CurrencyListAdapter()
        rv_currency.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_currency.isNestedScrollingEnabled = false
        rv_currency.adapter = currencyAdapter

        viewModel.currencyLiveResult.observe(this, Observer {
            if (it == null) return@Observer

            if (it.success) {
                currencyModel = it
                fetchInitialCurrency()
            }
        })

        viewModel.currencyListResult.observe(this, Observer {
            if (it == null) return@Observer
            val currencyArr = it.currencies.toString().replace("{", "").replace("}", "").split(",")
            val codeStrArr = ArrayList<String>()
            for (item in currencyArr) {
                codeStrArr.add(
                    item.substring(1, 4)
                )
            }

            //setup spinner
            val aa: ArrayAdapter<*> =
                ArrayAdapter<Any?>(
                    this, android.R.layout.simple_spinner_item,
                    codeStrArr as List<Any?>
                )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = aa
            spinner.onItemSelectedListener = this
            for (i in 0 until spinner.count) {
                if (spinner.getItemAtPosition(i).toString() == currency) {
                    spinner.setSelection(i)
                    break
                }
            }

        })

        tiet_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                val editableStr = editable.toString()
                if (editableStr != "" && editableStr.matches("^[0-9.]*$".toRegex())) {
                    amount = editable.toString().toDouble()
                    fetchSwitchedCurrency()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun fetchInitialCurrency() {
        val quoteArr = currencyModel.quotes.toString().replace("{", "").replace("}", "").split(",")
        quoteObjArr.clear()
        for (item in quoteArr) {
            quoteObjArr.add(
                Quote(
                    item.substring(4, 7),
                    item.substring(9, item.length).toDouble() * amount
                )
            )
        }

        currencyAdapter.setData(quoteObjArr)
    }

    private fun fetchSwitchedCurrency() {
        for (item in quoteObjArr) {
            if (item.code == currency)
                currencyValue = item.value
        }

        val switchedCurrencyArr = ArrayList<Quote>()
        for (item in quoteObjArr) {
            switchedCurrencyArr.add(
                Quote(
                    item.code,
                    item.value / currencyValue * amount
                )
            )
        }

        currencyAdapter.setData(switchedCurrencyArr)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currency = spinner.getItemAtPosition(p2).toString()
        tiet_amount.setText("1")
        fetchSwitchedCurrency()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}