package com.afrosoft.csadatacenter.ui.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.Interest
import com.afrosoft.csadatacenter.databinding.FragmentMarketBinding
import com.afrosoft.csadatacenter.databinding.SingleMarketPricesBinding
import com.afrosoft.csadatacenter.models.FarmerInterest
import com.afrosoft.csadatacenter.ui.home.FarmerInterestsAdapter

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

        binding.interestsRv.adapter = FarmerInterestsAdapter(requireActivity(),  mutableListOf(Interest("Beans"), Interest("Rice"), Interest("Maize"))){}

        adapter = MarketPriceAdapter(requireContext(), mutableListOf(MarketPrice(), MarketPrice(),MarketPrice(),MarketPrice(),MarketPrice(),MarketPrice()))
        binding.rvMarketPrices.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MarketPrice()

class MarketPriceAdapter(val context: Context, var list: MutableList<MarketPrice>)
    : RecyclerView.Adapter<MarketPriceAdapter.MarketPriceHolder>() {

    inner class MarketPriceHolder(val binding : SingleMarketPricesBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: MarketPrice) {
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
}



