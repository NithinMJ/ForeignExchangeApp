package com.nithin.foreignexchangeapp.ExchangeRatesFormatter

interface ExchangeRateFromatterInterface {

    fun getListFromMap(listMapContent:Map<String,Double>):ArrayList<String>
}