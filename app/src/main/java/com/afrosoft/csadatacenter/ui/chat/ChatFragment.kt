package com.afrosoft.csadatacenter.ui.chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.R
import com.afrosoft.csadatacenter.databinding.FragmentChatBinding
import com.afrosoft.csadatacenter.databinding.SingleSpecialistsBinding

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

        adapter = SpecialistAdapter(requireContext(), mutableListOf(Specialist(),Specialist(),Specialist(),Specialist(),))
        binding.rvSpecialists.adapter = adapter

    }

    private fun showExtensionWorkers() {
        binding.buttonExtensionWorkers.setBackgroundResource(R.drawable.black_filled_bg)
        binding.buttonExtensionWorkers.setTextColor(Color.WHITE)

        binding.buttonAgronomists.setBackgroundResource(R.drawable.black_border_bg)
        binding.buttonAgronomists.setTextColor(Color.BLACK)
    }
    private fun showAgronomists() {
        binding.buttonAgronomists.setBackgroundResource(R.drawable.black_filled_bg)
        binding.buttonAgronomists.setTextColor(Color.WHITE)

        binding.buttonExtensionWorkers.setBackgroundResource(R.drawable.black_border_bg)
        binding.buttonExtensionWorkers.setTextColor(Color.BLACK)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class Specialist()

class SpecialistAdapter(val context: Context, var list: MutableList<Specialist>)
    : RecyclerView.Adapter<SpecialistAdapter.SpecialistHolder>() {

    inner class SpecialistHolder(val binding : SingleSpecialistsBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun displayViews(obj: Specialist) {
            binding.root.setOnClickListener {
                context.startActivity(Intent(context,SpecialistActivity::class.java))
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
}