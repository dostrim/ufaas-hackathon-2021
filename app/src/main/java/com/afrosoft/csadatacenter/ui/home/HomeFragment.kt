package com.afrosoft.csadatacenter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.afrosoft.csadatacenter.Interest
import com.afrosoft.csadatacenter.databinding.FragmentHomeBinding
import com.afrosoft.csadatacenter.models.Attack
import com.afrosoft.csadatacenter.models.FarmerInterest
import com.afrosoft.csadatacenter.models.Practice

class HomeFragment : Fragment() {

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

        binding.interestsRv.adapter = FarmerInterestsAdapter(requireActivity(), mutableListOf(
            Interest("Beans"),
            Interest("Rice"),
            Interest("Maize")
        )){

        }

        binding.diseaseAttacksRv.adapter = AttacksAdapter(requireActivity(), mutableListOf(
            Attack(),Attack(),Attack(),Attack(),Attack(),Attack(),Attack()
        ))

        binding.pestAttacksRv.adapter = AttacksAdapter(requireActivity(), mutableListOf(
            Attack(),Attack(),Attack(),Attack(),Attack(),Attack(),Attack()
        ))
        binding.practicesRv.adapter = PracticeAdapter(requireActivity(), mutableListOf(
            Practice(),Practice()
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}