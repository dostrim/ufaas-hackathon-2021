package com.afrosoft.csadatacenter.ui.forum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afrosoft.csadatacenter.databinding.FragmentForumBinding
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.afrosoft.csadatacenter.ui.home.FarmerInterestsAdapter
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForumFragment : Fragment() {

    private lateinit var adapter: ForumAdapter
    lateinit var binding: FragmentForumBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForumBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.interestsRv.adapter = FarmerInterestsAdapter(requireActivity(),
            AppPreferences(requireActivity()).getFarmerInterests()
        ){}

        adapter = ForumAdapter(requireActivity(), mutableListOf()){
            fetchForum()
        }
        binding.forumRv.adapter = adapter

        binding.btnAskCommunity.setOnClickListener {
            startActivity(Intent(requireContext(),AskForumActivity::class.java))
        }

        fetchForum()
    }
    
    
    private fun fetchForum() {
        AndroidNetworking.post("http://lyk.rkl.mybluehost.me/agro_aid/api/get_forum.php")
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        Log.e(">>>","::${response}")
    
                        val list: MutableList<Forum> = Gson().fromJson(response, object : TypeToken<List<Forum?>?>() {}.type)
                        adapter.changeList(list)
                    }
    
                    override fun onError(anError: ANError?) {
                        Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_LONG).show()
                    }
                } )
            }
    
}
