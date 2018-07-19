package com.nithin.foreignexchangeapp.ForexRatesByDateScreen

interface ForexRatesByDateContract {
    interface View {
        fun displayForexRatesByDate(exchangeRatesByDate: ArrayList<String>)
    }

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun loadForexRatesByDate(userEnteredDate: String)

    }
}