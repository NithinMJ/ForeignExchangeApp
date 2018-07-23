package com.nithin.foreignexchangeapp.ForexRatesByDateScreen

import com.nithin.foreignexchangeapp.ExchangeRatesFormatter.ExchangeRateFormatterInterface
import com.nithin.foreignexchangeapp.Network.ForexDataClass
import com.nithin.foreignexchangeapp.Network.ForexRates
import com.nithin.foreignexchangeapp.Network.ForexRetrofitServiceInterface
import com.nithin.foreignexchangeapp.anyNotNull
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class ForexRatesByDatePresenterTest {

    private val forexRetrofitService = mock(ForexRetrofitServiceInterface::class.java)
    private val forexRateFormatter = mock(ExchangeRateFormatterInterface::class.java)
    private val mockView = mock(ForexRatesByDateContract.View::class.java)

    private val presenter = spy(ForexRatesByDatePresenter(
            forexRetrofitService,
            forexRateFormatter
    ))

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.attachView(mockView)
    }

    @Test
    fun `WHEN api call is successful AND base currency is euro THEN displayForexRatesByDate is called AND euro is 1`() {

        //arrange
        val mockResponse: ForexDataClass = createMockResponse(
                base = "base",
                date = "date",
                rates = createMockRates()
        )

        val expectedMap: Map<String, Double> = createExpectedMapForEuroCurrency()

        given(forexRetrofitService.getRatesByDate("2018-01-15", "EUR"))
                .willReturn(Single.just(mockResponse))

        given(forexRateFormatter.getListFromMap(expectedMap))
                .willReturn(arrayListOf())

        //act
        presenter.loadForexRatesByDate("2018-01-15", "EUR")

        //assert
        verify(mockView).displayForexRatesByDate(arrayListOf())
    }

    @Test
    fun `WHEN api call is successful AND base currency is not euro THEN displayForexRatesByDate is called`() {

        //arrange
        val mockResponse: ForexDataClass = createMockResponse(
                base = "base",
                date = "date",
                rates = createMockRates()
        )

        val expectedMap: Map<String, Double> = createExpectedMapForNonEuroCurrency()

        given(forexRetrofitService.getRatesByDate("2017-11-01", "USD"))
                .willReturn(Single.just(mockResponse))

        given(forexRateFormatter.getListFromMap(expectedMap))
                .willReturn(arrayListOf())

        //act
        presenter.loadForexRatesByDate("2017-11-01", "USD")

        //assert
        verify(mockView).displayForexRatesByDate(arrayListOf())
    }

    @Test
    fun `WHEN api call fails THEN throw exception`() {

        //arrange
        val error: Exception = mock(Exception::class.java)

        given(forexRetrofitService.getRatesByDate(anyString(), anyString()))
                .willReturn(Single.error(error))
        doNothing()
                .`when`(error).printStackTrace()

        //act
        presenter.loadForexRatesByDate(anyString(), anyString())

        //assert
        verify(mockView, never()).displayForexRatesByDate(anyNotNull())
    }

    private fun createMockResponse(
            base: String,
            date: String,
            rates: ForexRates
    ): ForexDataClass =
            ForexDataClass(
                    base = base,
                    date = date,
                    rates = rates
            )

    private fun createMockRates(): ForexRates =
            ForexRates(
                    USD = 1.0,
                    CAD = 1.0,
                    GBP = 1.0,
                    INR = 1.0,
                    JPY = 1.0,
                    EUR = 1.0
            )

    private fun createExpectedMapForEuroCurrency(): Map<String, Double> =
            mapOf(
                    "USD" to 1.0,
                    "CAD" to 1.0,
                    "GBP" to 1.0,
                    "INR" to 1.0,
                    "JPY" to 1.0,
                    "EUR" to 1.0
            )

    private fun createExpectedMapForNonEuroCurrency(): Map<String, Double> =
            mapOf(
                    "USD" to 1.0,
                    "CAD" to 1.5,
                    "GBP" to 2.0,
                    "INR" to 100.0,
                    "JPY" to 134.0,
                    "EUR" to 1.1
            )

}