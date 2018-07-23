package com.nithin.foreignexchangeapp.LatestForexRatesScreen

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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class LatestForexRatePresenterTest {

    private val mockForexRetrofitServiceInterface = mock(ForexRetrofitServiceInterface::class.java)
    private val mockExchangeRateFormatterInterface = mock(ExchangeRateFormatterInterface::class.java)
    private val mockView = mock(LatestForexRateContract.View::class.java)

    private val presenter = LatestForexRatePresenter(
            mockForexRetrofitServiceInterface,
            mockExchangeRateFormatterInterface
    )

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter.attachView(mockView)
    }

    @Test
    fun `WHEN Forex API is successful AND base currency is euro THEN displayLatestForexRates is called AND euro rate is 1`() {
        // Arrange
        val mockRates = createForexRates(
                usd = 1.0,
                cad = 1.0,
                gbp = 1.0,
                inr = 1.0,
                jpy = 1.0,
                eur = 1.0
        )
        val mockResponse = createApiResponse(
                base = "some base",
                date = "some date",
                forexRates = mockRates
        )

        val expectedMap: Map<String, Double> = createMapForEurBaseCurrency(
                usd = 1.0,
                cad = 1.0,
                gbp = 1.0,
                inr = 1.0,
                jpy = 1.0
        )

        given(mockForexRetrofitServiceInterface.getLatest("EUR"))
                .willReturn(Single.just(mockResponse))
        given(mockExchangeRateFormatterInterface.getListFromMap(expectedMap))
                .willReturn(arrayListOf())

        // Act
        presenter.loadLatestForexRates("EUR")

        // Assert
        verify(mockView).displayLatestForexRates(arrayListOf())
    }

    @Test
    fun `WHEN API is successful AND base currency is not euro THEN displayLatestForexRates is called`() {
        //Arrange
        val mockRates = createForexRates(
                usd = 1.0,
                cad = 1.0,
                gbp = 1.0,
                inr = 1.0,
                jpy = 1.0,
                eur = 2.0
        )
        val mockResponse = createApiResponse(
                base = "some base",
                date = "some date",
                forexRates = mockRates
        )

        val expectedMap: Map<String, Double> = createMapForNonEuroCurrency(
                usd = 1.0,
                cad = 1.0,
                gbp = 1.0,
                inr = 1.0,
                jpy = 1.0,
                eur = 2.0
        )

        given(mockForexRetrofitServiceInterface.getLatest("USD"))
                .willReturn(Single.just(mockResponse))
        given(mockExchangeRateFormatterInterface.getListFromMap(expectedMap))
                .willReturn(arrayListOf())

        //Act
        presenter.loadLatestForexRates("USD")

        //Assert
        verify(mockView).displayLatestForexRates(arrayListOf())
    }

    @Test
    fun `WHEN Api call fails THEN throw exception`() {
        //arrange
        val error = mock(Exception::class.java)

        given(mockForexRetrofitServiceInterface.getLatest(anyString()))
                .willReturn(Single.error(error))
        doNothing()
                .`when`(error).printStackTrace()

        // Act
        presenter.loadLatestForexRates(anyString())

        //
        verify(mockView, never()).displayLatestForexRates(anyNotNull())
    }

    private fun createApiResponse(
            base: String,
            date: String,
            forexRates: ForexRates
    ): ForexDataClass =
            ForexDataClass(
                    base = base,
                    date = date,
                    rates = forexRates
            )

    private fun createForexRates(
            usd: Double,
            cad: Double,
            gbp: Double,
            inr: Double,
            jpy: Double,
            eur: Double
    ) = ForexRates(
            USD = usd,
            CAD = cad,
            GBP = gbp,
            INR = inr,
            JPY = jpy,
            EUR = eur
    )

    private fun createMapForEurBaseCurrency(
            usd: Double,
            cad: Double,
            gbp: Double,
            inr: Double,
            jpy: Double
    ): Map<String, Double> = mapOf(
            "EUR" to 1.0,
            "USD" to usd,
            "CAD" to cad,
            "GBP" to gbp,
            "INR" to inr,
            "JPY" to jpy
    )

    private fun createMapForNonEuroCurrency(
            usd: Double,
            cad: Double,
            gbp: Double,
            inr: Double,
            jpy: Double,
            eur: Double
    ): Map<String, Double> = mapOf(
            "EUR" to eur,
            "USD" to usd,
            "CAD" to cad,
            "GBP" to gbp,
            "INR" to inr,
            "JPY" to jpy
    )

}