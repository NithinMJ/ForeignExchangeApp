package com.nithin.foreignexchangeapp.Network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForexRetrofitServiceInterface {

    @GET("latest")
    fun getLatest(@Query(value = "base") baseCurrency: String): Single<ForexDataClass>

    @GET("{date_value}")
    fun getRatesByDate(@Path(value = "date_value") date: String,
                       @Query(value = "base") baseCurrency: String): Single<ForexDataClass>
}