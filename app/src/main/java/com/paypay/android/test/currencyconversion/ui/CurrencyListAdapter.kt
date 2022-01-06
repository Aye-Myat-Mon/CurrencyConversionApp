package com.paypay.android.test.currencyconversion.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paypay.android.test.currencyconversion.R
import com.paypay.android.test.currencyconversion.model.Quote
import kotlinx.android.synthetic.main.row_currency_item.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by ayemyatmon on 06,January,2022
 */
class CurrencyListAdapter :
    RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    private val data = mutableListOf<Quote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CurrencyListViewHolder(
            inflater.inflate(R.layout.row_currency_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Quote>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class CurrencyListViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(quote: Quote) {
            val df = DecimalFormat("0.00000")
            df.roundingMode = RoundingMode.UP

            view.tv_code.text = quote.code
            view.tv_amount.text = df.format(quote.value)
        }
    }
}