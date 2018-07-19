package com.nithin.foreignexchangeapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.nithin.foreignexchangeapp.R
import kotlinx.android.synthetic.main.exchange_rates_data_by_date.view.*

class ForexRatesByDateAdapter(private val context: Context, private var exchangeRatesByDateMapList: ArrayList<String>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.exchange_rates_data_by_date, parent, false)

        view.exchangeRateDataTextByDate.text = exchangeRatesByDateMapList[position]

        return view
    }

    override fun getItem(position: Int): Any {
        return exchangeRatesByDateMapList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return exchangeRatesByDateMapList.size
    }
}