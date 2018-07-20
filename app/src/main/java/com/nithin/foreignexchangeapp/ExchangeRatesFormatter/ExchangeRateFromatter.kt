package com.nithin.foreignexchangeapp.ExchangeRatesFormatter

class ExchangeRateFromatter : ExchangeRateFromatterInterface {

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
}