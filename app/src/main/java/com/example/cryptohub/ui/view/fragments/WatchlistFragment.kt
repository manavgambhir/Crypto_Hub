package com.example.cryptohub.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptohub.R
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.FragmentWatchlistBinding
import com.example.cryptohub.ui.adapter.MarketAdapter
import com.example.cryptohub.ui.viewmodel.MarketViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchlistFragment : Fragment() {

    private lateinit var binding: FragmentWatchlistBinding
    private lateinit var watchlist: ArrayList<String>
    private lateinit var watchlistItem: ArrayList<CryptoCurrencyListItem>

    val vm by lazy{
        ViewModelProvider(this)[MarketViewModel::class.java]
    }
    private var list = arrayListOf<CryptoCurrencyListItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistBinding.inflate(layoutInflater)
        readData()

        lifecycleScope.launch(Dispatchers.IO) {
            vm.fetchData()
            withContext(Dispatchers.Main) {
                vm.marketList.observe(viewLifecycleOwner, Observer {
                    if (!it.isNullOrEmpty()) {
                        list.clear()
                        list.addAll(it)
                    }
                })
                watchlistItem = ArrayList()
                watchlistItem.clear()

                for (watchData in watchlist) {
                    for (item in list) {
                        if (watchData == item.symbol) {
                            watchlistItem.add(item)
                        }
                    }
                }

                binding.spinKitView.visibility = GONE
                binding.watchlistRecyclerView.adapter = MarketAdapter(requireContext(), watchlistItem, "watchfragment")
            }
        }
        return binding.root
    }

    private fun readData() {
        val sharedPreferences = requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
        val type = object : TypeToken<ArrayList<String>>(){}.type
        watchlist = gson.fromJson(json,type)
    }

}