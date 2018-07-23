package com.nithin.foreignexchangeapp.ForexRatesByDateScreen

import android.support.annotation.VisibleForTesting
import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFormatterInterface
import com.nithin.foreignexchangeapp.Network.ForexDataClass
import com.nithin.foreignexchangeapp.Network.ForexRetrofitServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForexRatesByDatePresenter(
        private val forexExchangeService: ForexRetrofitServiceInterface,
        private val exchangeRateFormatterInterface: ExchangeRateFormatterInterface
) : ForexRatesByDateContract.Presenter {

    private var view: ForexRatesByDateContract.View? = null


    override fun attachView(view: ForexRatesByDateContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadForexRatesByDate(userEnteredDate: String, baseCurrency: String) {
        forexExchangeService.getRatesByDate(userEnteredDate, baseCurrency)
                .map { forexDataClass: ForexDataClass -> exchangeRateFormatterInterface.createCurrencyMap(baseCurrency, forexDataClass) }
                .map { map -> exchangeRateFormatterInterface.getListFromMap(map)  }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ listOfCurrencies ->
                    view?.displayForexRatesByDate(listOfCurrencies)
                }, { error ->
                    error.printStackTrace()
                })
    }


}

