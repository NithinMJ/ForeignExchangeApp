package com.nithin.foreignexchangeapp.di

import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFormatter
import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFormatterInterface
import com.nithin.foreignexchangeapp.Network.ForexRetrofitServiceInterface
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val baseURL = "https://exchangeratesapi.io/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getForexRetrofitService() = retrofit.create(ForexRetrofitServiceInterface::class.java)
}

object HelperModule {
    fun getExchangeRateFormatter(): ExchangeRateFormatterInterface = ExchangeRateFormatter()
}
