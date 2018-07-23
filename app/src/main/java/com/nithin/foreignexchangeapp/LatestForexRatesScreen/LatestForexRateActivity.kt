package com.nithin.foreignexchangeapp.LatestForexRatesScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nithin.foreignexchangeapp.Adapter.LatestForexRateAdapter
import com.nithin.foreignexchangeapp.R
import com.nithin.foreignexchangeapp.di.HelperModule
import com.nithin.foreignexchangeapp.di.NetworkModule
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_latest_forex_rate.*

class LatestForexRateActivity : AppCompatActivity(), LatestForexRateContract.View {

    private var latestForexRateContract: LatestForexRateContract.Presenter =
            LatestForexRatePresenter(
                    NetworkModule.getForexRetrofitService(),
                    HelperModule.getExchangeRateFormatter()
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_forex_rate)

        val baseCurrencyText = intent.getStringExtra("userEnteredBaseCurrency").toString()

        baseCurrencyLatestHeaderText.text = getString(R.string.latest_forex_rates_header,baseCurrencyText)

        latestForexRateContract.attachView(this)
        latestForexRateContract.loadLatestForexRates(baseCurrencyText)

    }

    override fun displayLatestForexRates(exchangeRates: ArrayList<String>) {

        val adapter = LatestForexRateAdapter(this@LatestForexRateActivity, exchangeRates)
        listExchangeRatesData.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        latestForexRateContract.detachView()
        Disposables.disposed()
    }


}
