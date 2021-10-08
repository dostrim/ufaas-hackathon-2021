package com.bebridge.android_template

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bebridge.android_template.fragment.HomeFragment

class PagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
  override fun getItem(position: Int): Fragment {
    return HomeFragment()
  }

  override fun getCount(): Int {
    return 5
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return "$position"
  }

}