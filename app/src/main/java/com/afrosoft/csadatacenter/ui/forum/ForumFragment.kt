package com.afrosoft.csadatacenter.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afrosoft.csadatacenter.databinding.FragmentForumBinding
import com.afrosoft.csadatacenter.ui.AppPreferences
import com.afrosoft.csadatacenter.ui.home.FarmerInterestsAdapter

class ForumFragment : Fragment() {

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

        binding.forumRv.adapter = ForumAdapter(requireActivity(), mutableListOf(
            Forum(),Forum(),Forum(),Forum(),Forum(),Forum(),Forum(),Forum(),Forum()
        ))
    }
}