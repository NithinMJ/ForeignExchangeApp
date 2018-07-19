package com.nithin.foreignexchangeapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.nithin.foreignexchangeapp.R
import kotlinx.android.synthetic.main.exchange_rates_data.view.*

class LatestForexRateAdapter(private val context: Context, private var exchangeRatesMapList: ArrayList<String>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.exchange_rates_data,parent,false)

        view.exchangeRateDataText.text = exchangeRatesMapList[position]

        return view
    }

    override fun getItem(position: Int): Any {
        return exchangeRatesMapList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return exchangeRatesMapList.size
    }
}