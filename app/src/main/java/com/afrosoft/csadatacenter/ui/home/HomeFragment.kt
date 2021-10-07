package com.afrosoft.csadatacenter.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afrosoft.csadatacenter.InterestsActivity
import com.afrosoft.csadatacenter.MainActivity
import com.afrosoft.csadatacenter.databinding.DialogPlantingDateBinding
import com.afrosoft.csadatacenter.databinding.FragmentHomeBinding
import com.afrosoft.csadatacenter.models.*
import com.afrosoft.csadatacenter.network.NetworkClient
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private var interest: Interest? = null
    private lateinit var diseaseAdapter: AttacksAdapter
    private lateinit var pestAdapter: AttacksAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AndroidNetworking.initialize(requireActivity())

        binding.interestsRv.adapter = FarmerInterestsAdapter(requireActivity(),
            AppPreferences(requireActivity()).getFarmerInterests()
        ){
            binding.pestsTitle.text = "Likely Pest attacks for ${it?.plant_name}"
            binding.diseasesTitle.text = "Likely Disease attacks for ${it?.plant_name}"

            interest = it
            getHomeData()

            getCloseMarketPrice()
        }

        /*disease adapter instance*/
        diseaseAdapter = AttacksAdapter(requireActivity(), mutableListOf())
        binding.diseaseAttacksRv.adapter = diseaseAdapter

        /*pests adapter instance*/
        pestAdapter = AttacksAdapter(requireActivity(), mutableListOf())
        binding.pestAttacksRv.adapter = pestAdapter

        binding.practicesRv.adapter = PracticeAdapter(requireActivity(), mutableListOf(
            Practice(),Practice()
        ))

        binding.addInterest.setOnClickListener {
            startActivity(Intent(requireActivity(),InterestsActivity::class.java))
        }


    }

    private fun getCloseMarketPrice() {

        AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/get_market_prices.php")
            .addBodyParameter("plant_id",interest?.id)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.e(">>>","::${response}")

                    val list: MutableList<MarketPrice> = Gson().fromJson(response, object : TypeToken<List<MarketPrice?>?>() {}.type)
                    val price = list.firstOrNull()
                    binding.priceMarket.text = price?.market?.name
                    binding.priceAmount.text = price?.price
                    binding.priceWeight.text = "Per ${price?.units}"

                    //adapter.changeList(list)
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_LONG).show()
                }
            } )

    }

    private fun getHomeData() {
        AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/get_home.php")
                    .addBodyParameter("farmer_id",AppPreferences(requireContext()).getUser()?.id)
                    .addBodyParameter("plant_id",interest?.id)
                    .build()
                    .getAsString(object : StringRequestListener {
                        override fun onResponse(response: String?) {
        
                            val nrf = Gson().fromJson(response, HomeData::class.java)
                            if (nrf.status_code == 200){
                                //action
                                diseaseAdapter.changeData(nrf.diseases)
                                pestAdapter.changeData(nrf.pests)



                            }else{
                                showSetPlantingDate()
                            }

                            Toast.makeText(requireContext(), nrf.status_message, Toast.LENGTH_SHORT).show()
                        }
        
                        override fun onError(anError: ANError?) {
                            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                        }
                    })
    }

    private fun showSetPlantingDate() {
        val bindingD = DialogPlantingDateBinding.inflate(layoutInflater,null,false)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bindingD.root)
            .setCancelable(false)
            .create()

        bindingD.submit.setOnClickListener {

                AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/update_farmers_plants_plantingdate.php")
                 .addBodyParameter("planting_date",bindingD.plantingDate.text.toString())
                 .addBodyParameter("plant_id",interest?.id)
                 .addBodyParameter("farmer_id",AppPreferences(requireContext()).getUser()?.id)
                 .build()
                 .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {

                        val nrf = Gson().fromJson(response, UserResponse::class.java)
                        if (nrf.status_code == 200){
                            //action
                            getHomeData()
                        }

                        Toast.makeText(requireContext(), nrf.status_message, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                })

        }

        dialog.show()
    }

    private fun fetchLikelyDiseases(id: String?) {
        binding.diseasePb.visibility = View.VISIBLE
        AndroidNetworking.post("${NetworkClient().baseUrl}AgroAidApis/retrieve_plant_diseases.php")
            .addBodyParameter("id",id)
            .doNotCacheResponse()
            .build()
            .getAsObjectList(Attack::class.java, object : ParsedRequestListener<MutableList<Attack>> {
                override fun onResponse(response: MutableList<Attack>) {
                    try {

                        binding.diseasePb.visibility = View.GONE
                        Log.d("TAG-disease", "onResponse: $response")
                        diseaseAdapter.list = response
                        diseaseAdapter.notifyDataSetChanged()

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError?) {
                    binding.diseasePb.visibility = View.GONE
                    Log.d("TAG-disease", "onError: ${anError?.message}")
                    Toast.makeText(requireActivity(),"Connection Failed",Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchLikelyPests(id: String?) {
        binding.pestsPb.visibility = View.VISIBLE

        AndroidNetworking.post("${NetworkClient().baseUrl}AgroAidApis/retrieve_plant_pests.php")
            .addBodyParameter("id",id)
            .doNotCacheResponse()
            .build()
            .getAsObjectList(Attack::class.java, object : ParsedRequestListener<MutableList<Attack>> {
                override fun onResponse(response: MutableList<Attack>) {
                    try {
                        binding.pestsPb.visibility = View.GONE
                        Log.d("TAG-disease", "onResponse: $response")
                        pestAdapter.list = response
                        pestAdapter.notifyDataSetChanged()

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError?) {
                    binding.pestsPb.visibility = View.GONE
                    Log.d("TAG-disease", "onError: ${anError?.message}")
                    Toast.makeText(requireActivity(),"Connection Failed",Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}