package com.example.cryptohub.ui.adapter

import android.content.Context
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.R
import com.example.cryptohub.data.models.CryptoCurrencyListItem
import com.example.cryptohub.databinding.TopCurrencyLayoutBinding
import com.example.cryptohub.fragments.HomeFragmentDirections

class TopMarketAdapter(private var context:Context, private val list: List<CryptoCurrencyListItem>): RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>() {

    class TopMarketViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding = TopCurrencyLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
        return TopMarketViewHolder(LayoutInflater.from(context).inflate(R.layout.top_currency_layout,parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {
        val item = list[position]
        holder.binding.topCurrencyNameTextView.text = item.name
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ item.id + ".png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.topCurrencyImageView)

        val value:Double = item.quotes!![0]?.percentChange24h as Double
        if(value > 0 ){
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.green))
            holder.binding.topCurrencyChangeTextView.text = "+${String.format("%.02f",item.quotes[0]?.percentChange24h)} %"
        }
        else {
            holder.binding.topCurrencyChangeTextView.setTextColor(context.resources.getColor(R.color.red))
            holder.binding.topCurrencyChangeTextView.text = " ${String.format("%.02f", item.quotes[0]?.percentChange24h)} %"
        }

        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item)
            )
        }
    }
}