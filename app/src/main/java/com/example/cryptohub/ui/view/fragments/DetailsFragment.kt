package com.example.cryptohub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptohub.R
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val item: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val data: CryptoCurrencyListItem = item.data!!

        setUpDetails(data)

        loadChart(data)

        setButtonOnClick(data)

        addToWatchlist()

        binding.backStackButton.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment2())
        }

        moreDetails(data)

        return binding.root
    }

    private fun moreDetails(data: CryptoCurrencyListItem) {
        binding.valName.text = data.name
        binding.valCap.text = "$"+ "${data.quotes?.get(0)!!.marketCap}"
        binding.valVol.text = "$"+ "${data.quotes?.get(0)!!.volume24h}"
        val chg7d = data.quotes[0]!!.percentChange7d
        if(chg7d!! > 0 ){
            binding.val7d.setTextColor(context?.resources!!.getColor(R.color.green))
            binding.val7d.text = "${chg7d}%"
        }
        else{
            binding.val7d.setTextColor(context?.resources!!.getColor(R.color.red))
            binding.val7d.text = "${chg7d}%"
        }
        val chg30d = data.quotes[0]!!.percentChange30d
        if(chg30d!! > 0 ){
            binding.val30d.setTextColor(context?.resources!!.getColor(R.color.green))
            binding.val30d.text = "${chg30d}%"
        }
        else{
            binding.val30d.setTextColor(context?.resources!!.getColor(R.color.red))
            binding.val30d.text = "${chg30d}%"
        }
        binding.valSup.text = data.totalSupply.toString()
    }

    private var isStarSelected = false
    private fun addToWatchlist() {
        binding.addWatchlistButton.setOnClickListener {
            isStarSelected = !isStarSelected // Toggle the state

            val starResource = if (isStarSelected) R.drawable.ic_star else R.drawable.ic_star_outline
            binding.addWatchlistButton.setImageResource(starResource)
        }
    }

    private fun setUpDetails(data: CryptoCurrencyListItem) {
        binding.detailSymbolTextView.text = data.symbol

        Glide.with(requireContext()).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ data.id + ".png")
            .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
            .into(binding.detailImageView)

        binding.detailPriceTextView.text = "${String.format("$%.4f",data.quotes?.get(0)?.price)}"

        val value:Double = data.quotes!![0]?.percentChange24h as Double
        if(value > 0 ){
            binding.detailChangeTextView.setTextColor(context?.resources!!.getColor(R.color.green))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_up)
            binding.detailChangeTextView.text = "+${String.format("%.02f",data.quotes[0]?.percentChange24h)} %"
        }
        else{
            binding.detailChangeTextView.setTextColor(context?.resources!!.getColor(R.color.red))
            binding.detailChangeImageView.setImageResource(R.drawable.ic_caret_down)
            binding.detailChangeTextView.text = " ${String.format("%.02f",data.quotes[0]?.percentChange24h)} %"
        }
    }

    fun loadChart(item: CryptoCurrencyListItem) {
        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)

        binding.detaillChartWebView.loadUrl("https://www.tradingview.com/widgetembed/?hideideas=1&overrides=%7B%7D&enabled_features=%5B%5D&disabled_features=%5B%5D&locale=en#%7B%22symbol%22%3A%22" +
                item.symbol + "USD%22%2C%22frameElementId%22%3A%22tradingview_6b14f%22%2C%22interval%22%3A%22D%22%2C%22hide_legend%22%3A%221%22%2C%22hide_side_toolbar%22%3A%221%22%2C%22" +
                "allow_symbol_change%22%3A%221%22%2C%22save_image%22%3A%221%22%2C%22studies%22%3A%22%5B%5D%22%2C%22theme%22%3A%22dark%22%2C%22style%22%3A%221%22%2C%22timezone%22%3A%22Etc%2FUTC%22%2C%22studies_overrides%22%3A%22%7B%7D%22%2C%22utm_source%22%3A%22" +
                "www.tradingview.com%22%2C%22utm_medium%22%3A%22widget_new%22%2C%22utm_campaign%22%3A%22chart%22%2C%22utm_term%22%3A%22BITSTAMP%3ABTCUSD%22%2C%22page-uri%22%3A%22www.tradingview.com%2Fwidget%2Fadvanced-chart%2F%22%7D"
        )

//        binding.detaillChartWebView.loadUrl("https://in.tradingview.com/chart/?symbol="+ item.symbol +"USD&interval=D")
    }

    private fun setButtonOnClick(item: CryptoCurrencyListItem) {
        val oneMonth = binding.button
        val oneWeek = binding.button1
        val oneDay = binding.button2
        val fourHour = binding.button3
        val oneHour = binding.button4
        val fifteenMin = binding.button5

        val clickListener = View.OnClickListener {
            when(it.id){
                fifteenMin.id -> loadChartData(it,"15",item,oneDay,oneMonth,oneWeek,fourHour,oneHour)
                oneHour.id -> loadChartData(it,"1H",item,oneDay,oneMonth,oneWeek,fourHour,fifteenMin)
                fourHour.id -> loadChartData(it,"4H",item,oneDay,oneMonth,oneWeek,oneHour,fifteenMin)
                oneDay.id -> loadChartData(it,"D",item,fourHour,oneMonth,oneWeek,oneHour,fifteenMin)
                oneWeek.id -> loadChartData(it,"W",item,fourHour,oneMonth,oneDay,oneHour,fifteenMin)
                oneMonth.id -> loadChartData(it,"M",item,fourHour,oneWeek,oneDay,oneHour,fifteenMin)
            }
        }

        fifteenMin.setOnClickListener(clickListener)
        oneHour.setOnClickListener(clickListener)
        fourHour.setOnClickListener(clickListener)
        oneDay.setOnClickListener(clickListener)
        oneWeek.setOnClickListener(clickListener)
        oneMonth.setOnClickListener(clickListener)

    }

    private fun loadChartData(
        it: View?,
        s: String,
        item: CryptoCurrencyListItem,
        oneDay: AppCompatButton,
        oneMonth: AppCompatButton,
        oneWeek: AppCompatButton,
        fourHour: AppCompatButton,
        oneHour: AppCompatButton
    ) {
        disableBtn(oneDay,oneMonth,oneWeek,fourHour,oneHour)
        it!!.setBackgroundResource(R.drawable.active_button)

        binding.detaillChartWebView.settings.javaScriptEnabled = true
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null)

//        binding.detaillChartWebView.loadUrl("https://www.tradingview.com/widgetembed/?hideideas=1&overrides=%7B%7D&enabled_features=%5B%5D&disabled_features=%5B%5D&locale=en#%7B%22symbol%22%3A%22" +
//                item.symbol + "USD%22%2C%22frameElementId%22%3A%22tradingview_6b14f%22%2C%22interval%22%3A%22"+s+"%22%2C%22hide_legend%22%3A%221%22%2C%22hide_side_toolbar%22%3A%221%22%2C%22" +
//                "allow_symbol_change%22%3A%221%22%2C%22save_image%22%3A%221%22%2C%22studies%22%3A%22%5B%5D%22%2C%22theme%22%3A%22dark%22%2C%22style%22%3A%221%22%2C%22timezone%22%3A%22Etc%2FUTC%22%2C%22studies_overrides%22%3A%22%7B%7D%22%2C%22utm_source%22%3A%22" +
//                "www.tradingview.com%22%2C%22utm_medium%22%3A%22widget_new%22%2C%22utm_campaign%22%3A%22chart%22%2C%22utm_term%22%3A%22BITSTAMP%3ABTCUSD%22%2C%22page-uri%22%3A%22www.tradingview.com%2Fwidget%2Fadvanced-chart%2F%22%7D"
//        )

        binding.detaillChartWebView.loadUrl("https://in.tradingview.com/chart/?symbol="+ item.symbol +"USD&interval=" + s)
    }

    private fun disableBtn(oneDay: AppCompatButton, oneMonth: AppCompatButton, oneWeek: AppCompatButton, fourHour: AppCompatButton, oneHour: AppCompatButton) {
        oneDay.background = null
        oneMonth.background = null
        oneWeek.background = null
        fourHour.background = null
        oneHour.background = null
    }


}