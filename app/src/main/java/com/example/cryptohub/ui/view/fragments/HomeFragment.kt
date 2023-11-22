package com.example.cryptohub.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptohub.R
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.FragmentHomeBinding
import com.example.cryptohub.ui.adapter.TopLossGainPagerAdapter
import com.example.cryptohub.ui.adapter.TopMarketAdapter
import com.example.cryptohub.ui.viewmodel.MarketViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    val vm by lazy{
        ViewModelProvider(this)[MarketViewModel::class.java]
    }

    private val list = arrayListOf<CryptoCurrencyListItem>()

    private lateinit var adapter:TopMarketAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        adapter =  TopMarketAdapter(requireContext(),list)

        binding.topCurrencyRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) { getTopCurrencyList() }

        setTabLayout()

        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position==0){
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                }
                else{
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.contentViewPager){
            tab, position ->
            var title = if(position==0){
                "Top Gainers"
            }
            else{
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }

    private suspend fun getTopCurrencyList() {
        vm.fetchData()
//        val res = vm.marketList.value
//        Log.d("RES", "getTopCurrencyList: $res")
        withContext(Dispatchers.Main){
            binding.topCurrencyRecyclerView.let {
                vm.marketList.observe(viewLifecycleOwner, Observer {
                    if (!it.isNullOrEmpty()) {
                        list.clear()
                        list.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                })
            }
        }
    }
}