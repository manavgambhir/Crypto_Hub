package com.example.cryptohub.data.repos

import com.example.cryptohub.data.api.Client

object MarketRepository {
    suspend fun getMarketData() = Client.api.getMarketData()
}