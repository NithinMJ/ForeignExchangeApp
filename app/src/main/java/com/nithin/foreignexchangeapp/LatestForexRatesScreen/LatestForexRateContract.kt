package com.nithin.foreignexchangeapp.LatestForexRatesScreen

import java.util.*

interface LatestForexRateContract {

    interface View {
        fun displayLatestForexRates(exchangeRates: ArrayList<String>)
    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun loadLatestForexRates(baseCurrency: String)

    }
}