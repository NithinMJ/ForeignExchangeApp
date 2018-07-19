package com.nithin.foreignexchangeapp.ForexRatesByDateScreen

import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFromatter
import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFromatterInterface
import com.nithin.foreignexchangeapp.HomeScreen.baseURL
import com.nithin.foreignexchangeapp.Network.ForexDataClass
import com.nithin.foreignexchangeapp.Network.ForexDataLatest
import com.nithin.foreignexchangeapp.Network.ForexRetrofitServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ForexRatesByDatePresenter : ForexRatesByDateContract.Presenter {

    private var view: ForexRatesByDateContract.View? = null
    private var listRatesByDate: Map<String, Double> = mapOf()
    private var exchangeRateFromatterInterface: ExchangeRateFromatterInterface = ExchangeRateFromatter()

    override fun attachView(view: ForexRatesByDateContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadForexRatesByDate(userEnteredDate: String) {
        val requestForexRatesDataByDate = Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ForexRetrofitServiceInterface::class.java)

        requestForexRatesDataByDate.getRatesByDate(userEnteredDate)
                .map { forexDataClass: ForexDataClass ->
                    ForexDataLatest(
                            base = forexDataClass.base,
                            date = forexDataClass.date,
                            rates = mapOf(
                                    "USD" to forexDataClass.rates.USD,
                                    "CAD" to forexDataClass.rates.CAD,
                                    "GBP" to forexDataClass.rates.GBP,
                                    "INR" to forexDataClass.rates.INR,
                                    "JPY" to forexDataClass.rates.JPY
                            )
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ forexDataLatest: ForexDataLatest ->
                    listRatesByDate += forexDataLatest.rates
                    view?.displayForexRatesByDate(exchangeRateFromatterInterface.getListFromMap(listRatesByDate))

                }, { error ->
                    error.printStackTrace()
                })
    }

}

