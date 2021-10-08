package com.bebridge.android_template.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bebridge.android_template.PagerAdapter
import com.bebridge.android_template.databinding.FragmentViewPagerBinding

class ViewPagerFragment: Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
    binding.viewpager.apply {
      adapter = PagerAdapter(childFragmentManager)
      adapter?.notifyDataSetChanged()
    }
    binding.slidingTabs.setupWithViewPager(binding.viewpager)
    return binding.root
  }
}