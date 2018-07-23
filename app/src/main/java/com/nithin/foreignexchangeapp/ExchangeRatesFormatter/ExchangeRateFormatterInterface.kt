package com.nithin.foreignexchangeapp.ExchangeRatesFormatter

import com.nithin.foreignexchangeapp.Network.ForexDataClass

interface ExchangeRateFormatterInterface {

    fun getListFromMap(listMapContent:Map<String,Double>):ArrayList<String>

    fun createCurrencyMap(baseCurrency: String, forexDataClass: ForexDataClass): Map<String, Double>
}