package com.paypay.android.test.currencyconversion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.paypay.android.test.currencyconversion.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getCurrency("USD")

        viewModel.currencyResult.observe(this, Observer {
            if (it == null) return@Observer
            Timber.d("amm: currency result $it")
        })
    }
}