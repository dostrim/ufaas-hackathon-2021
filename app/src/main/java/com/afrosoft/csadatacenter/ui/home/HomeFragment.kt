package com.afrosoft.csadatacenter.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afrosoft.csadatacenter.InterestsActivity
import com.afrosoft.csadatacenter.databinding.FragmentHomeBinding
import com.afrosoft.csadatacenter.models.Attack
import com.afrosoft.csadatacenter.models.Interest
import com.afrosoft.csadatacenter.models.Practice
import com.afrosoft.csadatacenter.network.NetworkClient
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener

class HomeFragment : Fragment() {

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

            /*get data for likely pests and diseases*/
            fetchLikelyPests(it?.id)
            fetchLikelyDiseases(it?.id)
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