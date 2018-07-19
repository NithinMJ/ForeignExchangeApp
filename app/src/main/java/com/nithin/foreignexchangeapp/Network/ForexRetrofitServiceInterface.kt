package com.nithin.foreignexchangeapp.Network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ForexRetrofitServiceInterface {

    @GET("latest")
    fun getLatest(): Single<ForexDataClass>

    @GET("{date_value}")
    fun getRatesByDate(@Path(value = "date_value") date: String): Single<ForexDataClass>
}