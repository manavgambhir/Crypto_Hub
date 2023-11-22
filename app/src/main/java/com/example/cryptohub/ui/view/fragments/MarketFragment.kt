package com.example.cryptohub.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.FragmentMarketBinding
import com.example.cryptohub.ui.adapter.MarketAdapter
import com.example.cryptohub.ui.viewmodel.MarketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.Locale


class MarketFragment : Fragment() {

    private lateinit var binding:FragmentMarketBinding

    val vm by lazy{
        ViewModelProvider(this)[MarketViewModel::class.java]
    }

    private var list = arrayListOf<CryptoCurrencyListItem>()
//    private val ogList = arrayListOf<CryptoCurrencyListItem>()
    private lateinit var adapter: MarketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)

        vm.fetchData()
        adapter = MarketAdapter(requireContext(), list , "market")

        binding.currencyRecyclerView.adapter = adapter

        vm.fetchData()

        vm.marketList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
//                ogList.addAll(it)
                adapter.notifyDataSetChanged()
                binding.spinKitView.visibility = GONE
            }
        })

        searchCoin()

        return binding.root
    }


    lateinit var searchText:String

    private fun searchCoin() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().toLowerCase(Locale.ROOT)
                updateRv()
            }
        })
    }

    private fun updateRv() {
        val data = ArrayList<CryptoCurrencyListItem>()
        for(item in list){
            var coinName = item.name?.toLowerCase(Locale.ROOT)
            var coinSymbol = item.symbol?.toLowerCase(Locale.ROOT)

            if(coinName!!.contains(searchText) || coinSymbol!!.contains(searchText)){
                data.add(item)
            }
        }

        adapter.updateData(data)
    }
}