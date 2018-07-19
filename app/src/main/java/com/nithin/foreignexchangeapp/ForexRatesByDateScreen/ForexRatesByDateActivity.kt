package com.nithin.foreignexchangeapp.ForexRatesByDateScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nithin.foreignexchangeapp.Adapter.ForexRatesByDateAdapter
import com.nithin.foreignexchangeapp.R
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_forex_rates_by_date.*

class ForexRatesByDateActivity : AppCompatActivity(), ForexRatesByDateContract.View {


    private var forexRatesByDateContract: ForexRatesByDateContract.Presenter = ForexRatesByDatePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forex_rates_by_date)

        val dateValueText = intent.getStringExtra("userEnteredDate").toString()

        forexRatesByDateContract.attachView(this)
        forexRatesByDateContract.loadForexRatesByDate(dateValueText)


    }

    override fun displayForexRatesByDate(exchangeRatesByDate: ArrayList<String>) {
        val adapter = ForexRatesByDateAdapter(this@ForexRatesByDateActivity, exchangeRatesByDate)
        listExchangeRatesDataByDate.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        forexRatesByDateContract.detachView()
        Disposables.disposed()
    }
}
