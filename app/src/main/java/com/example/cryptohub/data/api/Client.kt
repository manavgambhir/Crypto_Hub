package com.example.cryptohub.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Client {

    val gson:Gson = GsonBuilder().create()

    val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coinmarketcap.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api = retrofit.create(MarketServices::class.java)
}