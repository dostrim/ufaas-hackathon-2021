package com.afrosoft.csadatacenter.ui.market

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.FragmentMarketBinding
import com.afrosoft.csadatacenter.databinding.SingleMarketPricesBinding
import com.afrosoft.csadatacenter.models.MarketPrice
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.afrosoft.csadatacenter.ui.home.FarmerInterestsAdapter
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MarketFragment : Fragment() {

    private lateinit var adapter: MarketPriceAdapter
    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.interestsRv.adapter = FarmerInterestsAdapter(requireActivity(),  AppPreferences(requireActivity()).getFarmerInterests()){}

        adapter = MarketPriceAdapter(requireContext(), mutableListOf())
        binding.rvMarketPrices.adapter = adapter

        getCloseMarketPrice()
    }

    private fun getCloseMarketPrice() {

        AndroidNetworking.post("{BASE_URL}api/get_market_prices")
            .addBodyParameter("plant_id","6")
            .addBodyParameter("latitude","0.341421")
            .addBodyParameter("longitude","32.585129")
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.e(">>>","::${response}")

                    val list: MutableList<MarketPrice> = Gson().fromJson(response, object : TypeToken<List<MarketPrice?>?>() {}.type)
                    adapter.changeList(list)

                    val price = list.firstOrNull()
                    binding.priceMarket.text = "${price?.market?.name} | ${price?.created_at}"
                    binding.priceAmount.text = price?.price
                    binding.priceWeight.text = "Per ${price?.units}"

                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(requireContext(),"No Internet Connection", Toast.LENGTH_LONG).show()
                }
            } )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MarketPriceAdapter(val context: Context, var list: MutableList<MarketPrice>)
    : RecyclerView.Adapter<MarketPriceAdapter.MarketPriceHolder>() {

    inner class MarketPriceHolder(val binding : SingleMarketPricesBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: MarketPrice) {
            binding.priceAmount.text = obj.price
            binding.priceMarket.text = obj.market.name
            binding.priceWeight.text = "/${obj.units}"
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketPriceHolder {
        return MarketPriceHolder(SingleMarketPricesBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MarketPriceHolder, position: Int) {
        holder.displayViews(list[position])
    }

    override fun getItemCount(): Int = list.size
    fun changeList(list: MutableList<MarketPrice>) {
        this.list = list
        notifyDataSetChanged()
    }
}



