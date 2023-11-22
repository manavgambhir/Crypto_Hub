package com.example.cryptohub.data.api

import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.data.models.MarketModel
import retrofit2.Response
import retrofit2.http.GET

interface MarketServices {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>
}