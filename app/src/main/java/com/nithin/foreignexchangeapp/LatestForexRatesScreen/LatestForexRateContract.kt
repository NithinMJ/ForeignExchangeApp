package com.nithin.foreignexchangeapp.LatestForexRatesScreen

interface LatestForexRateContract {

    interface View {
        fun displayLatestForexRates(exchangeRates: ArrayList<String>)
    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun loadLatestForexRates()

    }
}