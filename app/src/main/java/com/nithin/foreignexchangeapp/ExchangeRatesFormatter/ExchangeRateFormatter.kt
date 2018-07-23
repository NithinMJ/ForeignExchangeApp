package com.nithin.foreignexchangeapp.ExchangeRatesFormatter

import com.nithin.foreignexchangeapp.Network.ForexDataClass

class ExchangeRateFormatter : ExchangeRateFormatterInterface {

    private fun reduceDecimalPoints(number: Double): String {
        return String.format("%.2f", number)
    }

    override fun getListFromMap(listMapContent: Map<String, Double>): ArrayList<String> {
        val listMapContentRates: ArrayList<String> = ArrayList()

        for ((key, value) in listMapContent) {
            val valueWithReducedDecimal = reduceDecimalPoints(value)
            listMapContentRates.add("$key -> $valueWithReducedDecimal")
        }

        return listMapContentRates
    }

    override fun createCurrencyMap(baseCurrency: String, forexDataClass: ForexDataClass): Map<String, Double> {
        return if (baseCurrency == "EUR") {
            mapOf(
                    "EUR" to 1.0,
                    "USD" to forexDataClass.rates.USD,
                    "CAD" to forexDataClass.rates.CAD,
                    "GBP" to forexDataClass.rates.GBP,
                    "INR" to forexDataClass.rates.INR,
                    "JPY" to forexDataClass.rates.JPY
            )
        } else {
            mapOf(
                    "EUR" to forexDataClass.rates.EUR,
                    "USD" to forexDataClass.rates.USD,
                    "CAD" to forexDataClass.rates.CAD,
                    "GBP" to forexDataClass.rates.GBP,
                    "INR" to forexDataClass.rates.INR,
                    "JPY" to forexDataClass.rates.JPY
            )
        }
    }
}