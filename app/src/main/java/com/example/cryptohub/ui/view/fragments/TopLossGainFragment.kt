package com.example.cryptohub.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.FragmentTopLossGainBinding
import com.example.cryptohub.ui.adapter.MarketAdapter
import com.example.cryptohub.ui.viewmodel.MarketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.Collections

class TopLossGainFragment : Fragment() {

    val vm by lazy{
        ViewModelProvider(this)[MarketViewModel::class.java]
    }

    private lateinit var binding: FragmentTopLossGainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentTopLossGainBinding.inflate(layoutInflater)

        lifecycleScope.launch(Dispatchers.IO) { getMarketData() }

        return binding.root
    }

    private suspend fun getMarketData() {
        val position = requireArguments().getInt("position")

        vm.fetchData()

        withContext(Dispatchers.Main){
            vm.marketList.observe(viewLifecycleOwner, Observer {dataItems->
                Collections.sort(dataItems!!){
                    item1,item2->(item2.quotes?.get(0)?.percentChange24h!!.toInt())
                    .compareTo(item1.quotes?.get(0)?.percentChange24h!!.toInt())
                }
//                dataItems.let {
//                    Log.d("DATA2", dataItems.toString())
//                }
                binding.spinKitView.visibility = GONE

                val list = ArrayList<CryptoCurrencyListItem>()

                if(position==0){
                list.clear()
                    for(i in 0..9){
                        list.add(dataItems[i])
                    }
                    binding.topGainLoseRecyclerView.adapter= MarketAdapter(
                        requireContext(),
                        list,
                        "home"
                    )
                }
                else{
                    list.clear()
                    for(i in 0..9){
                        list.add(dataItems[dataItems.size-1-i])
                    }
                    binding.topGainLoseRecyclerView.adapter= MarketAdapter(
                        requireContext(),
                        list,
                        "home"
                    )
                }
            })
        }
    }
}