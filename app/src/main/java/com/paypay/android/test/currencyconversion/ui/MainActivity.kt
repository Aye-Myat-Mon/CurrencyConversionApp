package com.paypay.android.test.currencyconversion.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paypay.android.test.currencyconversion.Constants
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
    lateinit var currencyModel: CurrencyModel
    lateinit var currencyAdapter: CurrencyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup recycler
        currencyAdapter = CurrencyListAdapter()
        rv_currency.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_currency.isNestedScrollingEnabled = false
        rv_currency.adapter = currencyAdapter

        //setup spinner
        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, Constants.CURRENCY_LIST)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa
        spinner.onItemSelectedListener = this
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == currency) {
                spinner.setSelection(i)
                break
            }
        }

        viewModel.currencyResult.observe(this, Observer {
            if (it == null) return@Observer

            if (it.success) {
                currencyModel = it
                convertCurrency()
            } else {
                Toast.makeText(this, "API does not support!", Toast.LENGTH_LONG)
                    .show()
                for (i in 0 until spinner.count) {
                    if (spinner.getItemAtPosition(i).toString() == "USD") {
                        spinner.setSelection(i)
                        break
                    }
                }
            }
        })

        tiet_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                val editableStr = editable.toString()
                if (editableStr != "" && editableStr.matches("^[0-9.]*$".toRegex())) {
                    amount = editable.toString().toDouble()
                    convertCurrency()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun convertCurrency() {
        val quoteArr = currencyModel.quotes.toString().replace("{", "").replace("}", "").split(",")
        val quoteObjArr = ArrayList<Quote>()
        for (item in quoteArr) {
            quoteObjArr.add(
                Quote(
                    item.substring(1, 7),
                    item.substring(9, item.length).toDouble() * amount
                )
            )
        }

        currencyAdapter.setData(quoteObjArr)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currency = spinner.getItemAtPosition(p2).toString()
        viewModel.getCurrency(currency)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}