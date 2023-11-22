package com.example.cryptohub.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptohub.data.api.Client
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.data.models.MarketModel
import com.example.cryptohub.data.repos.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketViewModel: ViewModel() {
    var marketList = MutableLiveData<List<CryptoCurrencyListItem>?>()
    fun fetchData(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = withContext(Dispatchers.IO) { MarketRepository.getMarketData() }
//            Log.d("LIST",response.body().toString())
            if(response.isSuccessful){
                val marketData = response.body()!!.data?.cryptoCurrencyList?.filterNotNull()
//                Log.d("LIST",marketData.toString())
                response.body().let {
                    if (it != null) {
                        marketList.postValue(marketData)
                    }
                }
            }
        }
    }
}