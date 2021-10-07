package com.afrosoft.csadatacenter.tasks

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.afrosoft.csadatacenter.network.NetworkClient
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener

class PlantsDataFetcher(context: Context, params: WorkerParameters): CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        fetchPlantsData()
        return Result.success()
    }

    private fun fetchPlantsData() {
        AndroidNetworking.get("${NetworkClient().baseUrl}retrieve_plants")
            .doNotCacheResponse()
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    Log.d("TAG-plants", "onResponse: $response")
                    AppPreferences(applicationContext).saveAllPlantsData(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d("TAG-plants", "onError: ")
                    fetchPlantsData()
                }
            })
    }
}