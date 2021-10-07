package com.afrosoft.csadatacenter.ui.chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.R
import com.afrosoft.csadatacenter.databinding.FragmentChatBinding
import com.afrosoft.csadatacenter.databinding.SingleSpecialistsBinding
import com.afrosoft.csadatacenter.models.Interest
import com.afrosoft.csadatacenter.models.Specialist
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ChatFragment : Fragment() {

    private lateinit var adapter: SpecialistAdapter
    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAgronomists.setOnClickListener {
            showAgronomists()
        }
        binding.buttonExtensionWorkers.setOnClickListener {
            showExtensionWorkers()
        }

        adapter = SpecialistAdapter(requireContext(), mutableListOf())
        binding.rvSpecialists.adapter = adapter

        binding.buttonAgronomists.callOnClick()
    }

    private fun showExtensionWorkers() {
        binding.buttonExtensionWorkers.setBackgroundResource(R.drawable.black_filled_bg)
        binding.buttonExtensionWorkers.setTextColor(Color.parseColor("#FF388e3c"))

        binding.buttonAgronomists.setBackgroundResource(R.drawable.black_border_bg)
        binding.buttonAgronomists.setTextColor(Color.BLACK)

        getWorkers("extension_workers")
    }
    private fun showAgronomists() {
        binding.buttonAgronomists.setBackgroundResource(R.drawable.black_filled_bg)
        binding.buttonAgronomists.setTextColor(Color.parseColor("#FF388e3c"))

        binding.buttonExtensionWorkers.setBackgroundResource(R.drawable.black_border_bg)
        binding.buttonExtensionWorkers.setTextColor(Color.BLACK)

        getWorkers("agronomists")
    }

    private fun getWorkers(type: String) {

        AndroidNetworking.post("{BASE_URL}api/get_workers")
            .addBodyParameter("type",type)
            .addBodyParameter("latitude","0.341421")
            .addBodyParameter("longitude","32.585129")
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.e(">>>","::${response}")

                    val list: MutableList<Specialist> = Gson().fromJson(response, object : TypeToken<List<Specialist?>?>() {}.type)
                    adapter.changeList(list)

                    val specialist = list.firstOrNull()
                    if (specialist!=null){
                        binding.specialistAddress.text = specialist?.location
                        binding.specialistName.text = specialist?.name
                        binding.specialistDescription.text = specialist?.description
                        binding.specialistSpeciality.text = specialist?.speciality
                        binding.specialistDistance.text = "${specialist?.distance} Km Away"
                    }

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


class SpecialistAdapter(val context: Context, var list: MutableList<Specialist>)
    : RecyclerView.Adapter<SpecialistAdapter.SpecialistHolder>() {

    inner class SpecialistHolder(val binding : SingleSpecialistsBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: Specialist) {

            binding.name.text = obj.name
            binding.distance.text = "${obj.distance} Km Away"
            binding.address.text = obj.location

            binding.root.setOnClickListener {
                context.startActivity(Intent(context,SpecialistActivity::class.java).putExtra("specialist",Gson().toJson(obj)))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistHolder {
        return SpecialistHolder(SingleSpecialistsBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: SpecialistHolder, position: Int) {
        holder.displayViews(list[position])
    }

    override fun getItemCount(): Int = list.size
    fun changeList(list: MutableList<Specialist>) {
        this.list = list
        notifyDataSetChanged()
    }
}