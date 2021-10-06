package com.afrosoft.csadatacenter.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.afrosoft.csadatacenter.Interest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppPreferences(val context: Context) {

    fun getInterests(): MutableList<Interest> {
        val list: MutableList<Interest> =
            Gson().fromJson(preferences.getString("interests","[]"), object : TypeToken<List<Interest?>?>() {}.type)
        return list

    }
    fun saveInterests(interests: MutableList<Interest>) {
        editor.putString("interests", Gson().toJson(interests)).apply()
    }

    val preferences = context.getSharedPreferences("AgriAid",MODE_PRIVATE)
    val editor = preferences.edit()
}