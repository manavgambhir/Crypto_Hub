package com.example.cryptohub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.R
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.CurrencyItemLayoutBinding
import com.example.cryptohub.fragments.HomeFragmentDirections
import com.example.cryptohub.fragments.MarketFragmentDirections
import com.example.cryptohub.fragments.WatchlistFragment
import com.example.cryptohub.fragments.WatchlistFragmentDirections

class MarketAdapter(private var context: Context, var list: List<CryptoCurrencyListItem>, var type: String):RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    class MarketViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var binding = CurrencyItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false))
    }

    override fun getItemCount(): Int = list.size

    fun updateData(dataList: List<CryptoCurrencyListItem>){
        list = dataList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ item.id + ".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        Glide.with(context).load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/"+ item.id + ".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        holder.binding.currencyPriceTextView.text = "${String.format("$%.02f",item.quotes?.get(0)?.price)}"

        val value:Double = item.quotes!![0]?.percentChange24h as Double
        if(value > 0 ){
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.currencyChangeTextView.text = "+${String.format("%.02f",item.quotes[0]?.percentChange24h)} %"
        }
        else{
            holder.binding.currencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.currencyChangeTextView.text = " ${String.format("%.02f",item.quotes[0]?.percentChange24h)} %"
        }

        holder.itemView.setOnClickListener {
            if(type == "home") {
                findNavController(it).navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
                )
            }
            else if(type == "market"){
                findNavController(it).navigate(
                    MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item)
                )
            }
            else{
                findNavController(it).navigate(
                    WatchlistFragmentDirections.actionWatchlistFragmentToDetailsFragment(item)
                )
            }
        }
    }
}