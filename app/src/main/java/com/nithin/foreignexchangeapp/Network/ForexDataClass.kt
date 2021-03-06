package com.nithin.foreignexchangeapp.Network

data class ForexDataClass(
        val base: String,
        val date: String,
        val rates: ForexRates
)

data class ForexRates(
        val USD: Double,
        val CAD: Double,
        val GBP: Double,
        val INR: Double,
        val JPY: Double,
        val EUR: Double
)