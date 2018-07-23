package com.nithin.foreignexchangeapp.LatestForexRatesScreen

import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFormatterInterface
import com.nithin.foreignexchangeapp.Network.ForexDataClass
import com.nithin.foreignexchangeapp.Network.ForexRetrofitServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LatestForexRatePresenter(
        private val forexExchangeService: ForexRetrofitServiceInterface,
        private val exchangeRateFormatterInterface: ExchangeRateFormatterInterface
) : LatestForexRateContract.Presenter {

    private var view: LatestForexRateContract.View? = null

    override fun attachView(view: LatestForexRateContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadLatestForexRates(baseCurrency: String) {
        forexExchangeService.getLatest(baseCurrency)
                .map { forexDataClass: ForexDataClass -> exchangeRateFormatterInterface.createCurrencyMap(baseCurrency, forexDataClass) }
                .map { map -> exchangeRateFormatterInterface.getListFromMap(map) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ listOfCurrencies ->
                    view?.displayLatestForexRates(listOfCurrencies)

                }, { error ->
                    error.printStackTrace()
                })
    }
}