package com.afrosoft.csadatacenter.tasks

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.afrosoft.csadatacenter.tasks.Constants.FAILURE_RESULT
import com.afrosoft.csadatacenter.tasks.Constants.LOCATION_DATA_EXTRA
import com.afrosoft.csadatacenter.tasks.Constants.RECEIVER
import com.afrosoft.csadatacenter.tasks.Constants.RESULT_DATA_KEY
import com.afrosoft.csadatacenter.tasks.Constants.SUCCESS_RESULT
import java.util.*

class AddressIntentService : IntentService("address_service") {

    lateinit var resultReceiver : ResultReceiver

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null){
            var errorMessage = ""
            resultReceiver = intent.getParcelableExtra(RECEIVER)!!
            val location: Location = intent.getParcelableExtra(LOCATION_DATA_EXTRA)!!

            val geocoder = Geocoder(this, Locale.getDefault())

            var addressList : List<Address>? = null
            try {
                addressList = geocoder.getFromLocation(location.latitude,location.longitude,1)
            }catch (e : Exception){
                errorMessage = e.message.toString()
            }

            if (addressList.isNullOrEmpty()){
                deliverResultsToReceiver(FAILURE_RESULT,errorMessage)
            }else
            {
                val address = addressList[0]
                val addressFragments = arrayListOf<String>()
                Log.d("addressF", "LOC> "+address.getAddressLine(0))
                Log.d("addressF", "LOC> "+address.locality)
                Log.d("addressF", "LOC> "+address.countryName)

                /*for (i in 0 until address.maxAddressLineIndex){
                    addressFragments.add(address.getAddressLine(i))
                }*/

                deliverResultsToReceiver(
                    SUCCESS_RESULT,
                    "${address.locality}, ${address.countryName}"
                )
            }


        }
    }

    fun deliverResultsToReceiver(resultCode : Int, addressMessage: String){
        val bundle = Bundle()
        bundle.putString(RESULT_DATA_KEY, addressMessage)
        resultReceiver.send(resultCode,bundle)
    }
}