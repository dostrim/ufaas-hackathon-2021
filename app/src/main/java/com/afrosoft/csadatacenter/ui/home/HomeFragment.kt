package com.afrosoft.csadatacenter.ui.home

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.afrosoft.csadatacenter.InterestsActivity
import com.afrosoft.csadatacenter.MainActivity
import com.afrosoft.csadatacenter.databinding.DialogPlantingDateBinding
import com.afrosoft.csadatacenter.databinding.FragmentHomeBinding
import com.afrosoft.csadatacenter.models.*
import com.afrosoft.csadatacenter.network.NetworkClient
import com.afrosoft.csadatacenter.tasks.AddressIntentService
import com.afrosoft.csadatacenter.tasks.Constants.LOCATION_DATA_EXTRA
import com.afrosoft.csadatacenter.tasks.Constants.RECEIVER
import com.afrosoft.csadatacenter.tasks.Constants.RESULT_DATA_KEY
import com.afrosoft.csadatacenter.tasks.Constants.SUCCESS_RESULT
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*

class HomeFragment : Fragment() {

    private var interest: Interest? = null
    private lateinit var diseaseAdapter: AttacksAdapter
    private lateinit var pestAdapter: AttacksAdapter
    private var _binding: FragmentHomeBinding? = null

    private val weatherApiKey = "29c558c529a627e853fe2146d56a3e4d"
    private val SingleDayWeatherApi = "http://api.openweathermap.org/data/2.5/weather"
    private lateinit var resultReceiver: ResultReceiver

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

        resultReceiver = AddressResultReceiver(Handler())

        AndroidNetworking.initialize(requireActivity())

        binding.containerCropData.visibility = View.GONE

        binding.interestsRv.adapter = FarmerInterestsAdapter(
            requireActivity(),
            AppPreferences(requireActivity()).getFarmerInterests()
        ) {
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

        binding.practicesRv.adapter = PracticeAdapter(
            requireActivity(), mutableListOf(
                Practice(), Practice()
            )
        )

        binding.addInterest.setOnClickListener {
            startActivity(Intent(requireActivity(), InterestsActivity::class.java))
        }

        getCurrentLocation()


    }

    private fun getCloseMarketPrice() {

        binding.containerCropPrice.visibility = View.GONE
        AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/get_market_prices.php")
            .addBodyParameter("plant_id", interest?.id)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.e(">>>", "::${response}")

                    binding.containerCropPrice.visibility = View.VISIBLE
                    val list: MutableList<MarketPrice> = Gson().fromJson(response, object : TypeToken<List<MarketPrice?>?>() {}.type)
                    val price = list.firstOrNull()
                    binding.priceMarket.text = price?.market?.name
                    binding.priceAmount.text = "UGX ${price?.price}"
                    binding.priceWeight.text = "Per ${price?.units}"

                    //adapter.changeList(list)
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG)
                        .show()
                }
            })

    }

    private fun getHomeData() {
        binding.containerCropData.visibility = View.GONE
        AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/get_home.php")
                    .addBodyParameter("farmer_id",AppPreferences(requireContext()).getUser()?.id)
                    .addBodyParameter("plant_id",interest?.id)
                    .build()
                    .getAsString(object : StringRequestListener {
                        override fun onResponse(response: String?) {

                            val nrf = Gson().fromJson(response, HomeData::class.java)
                            if (nrf.status_code == 200){
                                        //action
                                        binding.containerCropData.visibility = View.VISIBLE
                                        diseaseAdapter.changeData(nrf.diseases)
                                        pestAdapter.changeData(nrf.pests)
                                        binding.plantAge.text = "Between ${nrf.plant_age?.minimum} Days and ${nrf.plant_age?.maximum} Days"

                            } else {
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
        val bindingD = DialogPlantingDateBinding.inflate(layoutInflater, null, false)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(bindingD.root)
            .setCancelable(false)
            .create()

        val calender = Calendar.getInstance(Locale.getDefault())
        bindingD.plantingDate.setOnClickListener {
            DatePickerDialog(
                requireActivity(), { view, year, month, dayOfMonth ->
                    bindingD.plantingDate.setText("$year-${month.plus(1)}-$dayOfMonth")
                }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        bindingD.submit.setOnClickListener {
            bindingD.submit.text = "Please wait..."
            AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/update_farmers_plants_plantingdate.php")
                .addBodyParameter("planting_date", bindingD.plantingDate.text.toString())
                .addBodyParameter("plant_id", interest?.id)
                .addBodyParameter("farmer_id", AppPreferences(requireContext()).getUser()?.id)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {


                        bindingD.submit.text = "Submit"

                        val nrf = Gson().fromJson(response, UserResponse::class.java)
                        if (nrf.status_code == 200) {
                            //action
                            getHomeData()
                            dialog.dismiss()
                        }

                        Toast.makeText(requireContext(), nrf.status_message, Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()

                    }

                    override fun onError(anError: ANError?) {
                        bindingD.submit.text = "Submit"
                        Toast.makeText(requireContext(), "Connection Failed", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                })

        }

        dialog.show()
    }

    private fun fetchLikelyDiseases(id: String?) {
        binding.diseasePb.visibility = View.VISIBLE
        AndroidNetworking.post("${NetworkClient().baseUrl}AgroAidApis/retrieve_plant_diseases.php")
            .addBodyParameter("id", id)
            .doNotCacheResponse()
            .build()
            .getAsObjectList(
                Attack::class.java,
                object : ParsedRequestListener<MutableList<Attack>> {
                    override fun onResponse(response: MutableList<Attack>) {
                        try {

                            binding.diseasePb.visibility = View.GONE
                            Log.d("TAG-disease", "onResponse: $response")
                            diseaseAdapter.list = response
                            diseaseAdapter.notifyDataSetChanged()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        binding.diseasePb.visibility = View.GONE
                        Log.d("TAG-disease", "onError: ${anError?.message}")
                        Toast.makeText(requireActivity(), "Connection Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    private fun fetchLikelyPests(id: String?) {
        binding.pestsPb.visibility = View.VISIBLE

        AndroidNetworking.post("${NetworkClient().baseUrl}AgroAidApis/retrieve_plant_pests.php")
            .addBodyParameter("id", id)
            .doNotCacheResponse()
            .build()
            .getAsObjectList(
                Attack::class.java,
                object : ParsedRequestListener<MutableList<Attack>> {
                    override fun onResponse(response: MutableList<Attack>) {
                        try {
                            binding.pestsPb.visibility = View.GONE
                            Log.d("TAG-disease", "onResponse: $response")
                            pestAdapter.list = response
                            pestAdapter.notifyDataSetChanged()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        binding.pestsPb.visibility = View.GONE
                        Log.d("TAG-disease", "onError: ${anError?.message}")
                        Toast.makeText(requireActivity(), "Connection Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*location*/
    private fun getCurrentLocation() {

        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                200
            )

        } else {
            LocationServices.getFusedLocationProviderClient(requireActivity())
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult?) {
                        super.onLocationResult(p0)
                        LocationServices.getFusedLocationProviderClient(requireActivity())
                            .removeLocationUpdates(
                                this
                            )
                        if (p0 != null && p0.locations.size > 0) {
                            val latestLocationIndex = p0.locations.size - 1
                            val latitude = p0.locations[latestLocationIndex].latitude
                            val longitude = p0.locations[latestLocationIndex].longitude

                            fetchWeatherUpdates(latitude, longitude)

                            val location = Location("providerNA")
                            location.latitude = latitude
                            location.longitude = longitude
                            fetchAddressFromLatLng(location)
                        }
                    }
                }, Looper.getMainLooper())
        }


    }

    private fun fetchWeatherUpdates(latitude: Double, longitude: Double) {

        Log.d("TAG-coo", "fetchWeatherUpdates: $latitude - $longitude")
        AndroidNetworking.get("$SingleDayWeatherApi?lat=$latitude&lon=$longitude&appid=$weatherApiKey")
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    Log.d("WeatherResponse", "onResponse: $response")
                    try {
                        val jsonObj = JSONObject(response)
                        val weather =
                            jsonObj.getJSONArray("weather").getJSONObject(0) //desc,icon
                        val main = jsonObj.getJSONObject("main")

                        val temp = main.getString("temp").toDouble() - 273.15
                        val temp1: String = DecimalFormat("##.#").format(temp).toString() + "°C"
                        //String address = jsonObj.getString("name") + ", " + sys.getString("country");
                        val status = weather.getString("description")
                        val icon = weather.getString("icon")
                        binding.desc.text = status.lowercase()
                        binding.temp.text = temp1
                        Glide.with(requireActivity())
                            .load("http://openweathermap.org/img/wn/$icon@4x.png")
                            .into(binding.icon)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError) {
                    Log.d("weather_updates", "" + anError)
                    binding.desc.text = "No Internet"
                }
            })

    }

    private fun fetchAddressFromLatLng(location: Location) {
        requireActivity().startService(
            Intent(requireActivity(), AddressIntentService::class.java)
                .putExtra(RECEIVER, resultReceiver)
                .putExtra(LOCATION_DATA_EXTRA, location)
        )
    }

    inner class AddressResultReceiver(handler: Handler?) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == SUCCESS_RESULT) {
                binding.location.text = resultData!!.getString(RESULT_DATA_KEY)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(requireActivity(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }

        }
    }

}